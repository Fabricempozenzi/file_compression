
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

/**
 * Program: SchubsH
 *  Author : Fabrice Mpozenzi
 *  Date   : 05/06/2024
 *  Course : CS375 Software Engineering II
 *  Compile: mvn compile
 *  Execute: java -cp target/classes SchubsH src/test/resources/*.txt  
 */

public class SchubsH {

    // alphabet size of extended ASCII
    private static final int R = 256;

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

// ...

// compress bytes from file and write to standard output
public static void compress(String filePath, String outPath) {
    try {
        //check if file exists before attempting to read
        if(!Files.exists(Paths.get(filePath))){
            throw new RuntimeException(new IOException("File " + filePath + " does not exist"));
        }
        // read the input from file
        String s = new String(Files.readAllBytes(Paths.get(filePath)));
        char[] input = s.toCharArray();
        //System.out.println("input size: " +input.length);
        if(input.length==0){
            BinaryStdOut.close();
            return;
        }

        // tabulate frequency counts
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++) {
            if (input[i] < R) {
                freq[input[i]]++;
            } else {
                throw new RuntimeException("Invalid character: " + input[i]);
            }
        }


        // build Huffman trie
        Node root = buildTrie(freq);

        // build code table
        String[] st = new String[R];
        buildCode(st, root, "");

        //System.out.println("writing Trie to output");

        // print trie for decoder
        writeTrie(root);

        //System.out.println("writing input length of " + input.length );

        // print number of bytes in original uncompressed message
        BinaryStdOut.write(input.length);

        //System.out.println("encoding... ");

        // use Huffman code to encode input
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                    //System.out.print(0);
                }
                else if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                    //System.out.print(1);
                }
                else throw new RuntimeException("Illegal state");
            }
            //System.out.println("Char " + input[i] + " " + code);
        }
        System.out.println();

        // flush output stream
        BinaryStdOut.flush();
    } catch (IOException e) {
        throw new RuntimeException("Error reading file", e);
    }
}


    // build the Huffman trie given frequencies
    private static Node buildTrie(int[] freq) {

        // initialze priority queue with singleton trees
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for (char i = 0; i < R; i++)
            if (freq[i] > 0)
                pq.add(new Node(i, freq[i], null, null));

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left  = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.add(parent);
            //System.out.println("buildTrie: new parent, frequencies:" + left.freq +" and "+ right.freq);
        }
        return pq.poll();
    }


    // write bitstring-encoded trie to standard output
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st[x.ch] = s;
            //System.out.println("buildCode (" +s.length() + ")" + x.ch + " "+s);
        }
    }
    
    public static void main(String[] args) {
        if (args.length >= 1) {
            for (String uncompressedFileName : args) {
                String compressedFileName = uncompressedFileName+".hh";
                if(Files.exists(Paths.get(compressedFileName))){
                    System.out.println("Compressed file already exists: ");
                    return;
                }
                BinaryStdOut.setOutputFile(compressedFileName);
                compress(uncompressedFileName, compressedFileName);
                System.out.println("File (" + Paths.get(uncompressedFileName).getFileName().toString()+") " + "Compressed Successfully");
                BinaryStdOut.close();
            }
        } else {
            System.out.println("No command line arguments provided");
        }
    }

}
