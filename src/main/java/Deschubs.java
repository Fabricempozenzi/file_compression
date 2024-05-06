
import java.io.File;
import java.io.IOException;

/**
 * Program: Deschubs
 *  Author : Fabrice Mpozenzi
 *  Date   : 05/06/2024
 *  Course : CS375 Software Engineering II
 *  Compile: mvn compile
 *  java -cp target/classes Deschubs src/test/resources/*.hh / *.ll   
 */
public class Deschubs {
    private static final int R = 128;        // number of input chars
    private static final int L = 256;       // number of codewords = 2^W
    private static final int W = 8;         // codeword width
       // Node inner class
       private static class Node implements Comparable<Node> {
        private char ch;
        private int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
// expand Huffman-encoded input from standard input and write to standard output
    public static void DecompressHuffman(String inPath, String outPath) {
        //System.out.println("expand called with " + inPath + " " + outPath);
        try {
            String OriginalFileName= inPath.replace(".hh","");
            File OriginalFile = new File(OriginalFileName);
            if(OriginalFile.exists()) {
                System.out.println("Sorry, the File " + "("+ OriginalFile + ")" + " already exists");
                return;
            }
            else{
                System.out.println("Decompressed " + "("+ inPath + ")" + "successfully");
            }
            BinaryStdIn.setInputFile(inPath);
            BinaryStdOut.setOutputFile(outPath);

            // read in Huffman trie from input stream
            Node root = readTrie();  
            //System.out.println("Huffman trie: " + root);

            // number of bytes to write
            int length = BinaryStdIn.readInt();

            // decode using the Huffman trie
            for (int i = 0; i < length; i++) {
                Node x = root;
                while (!x.isLeaf()) {
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit) x = x.right;
                    else     x = x.left;
                }
                BinaryStdOut.write(x.ch);
                //System.out.println("Decoded character: " + x.ch);
            }
            BinaryStdOut.flush();
            BinaryStdOut.close();
            BinaryStdIn.close();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    // read a trie from standard input stream
    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new Node(BinaryStdIn.readChar(), -1, null, null);
        } else {
            return new Node('\0', -1, readTrie(), readTrie());
        }
    }  

    public static void DecompressLZW(String compressedFile, String outputFile) throws IOException {
        String originalFileName = compressedFile.replace(".ll", "");
        File originalFile = new File(originalFileName);
        if(originalFile.exists()){
            System.out.println("Sorry, the file " + "(" +originalFileName+")"+ " already exists");
            return;
        }
        else{
            System.out.println("Decompressed " + "("+ compressedFile + ") " + "successfully");
        }
        BinaryIn in = new BinaryIn(compressedFile);
        BinaryOut out = new BinaryOut(outputFile);
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = in.readInt(W);
        String val = st[codeword];

        StringBuilder encodedMessage = new StringBuilder(val);

        //System.out.println("in " + codeword + ":");
        //System.out.println("out " + val);

        while (true) {
            out.write(val);
            codeword = in.readInt(W);
            if (codeword == R) {
                //System.out.println("in: " + codeword);
                break;
            }
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) {
                st[i++] = val + s.charAt(0);
                //System.out.println("i<L " + val + s.charAt(0));
            }
            //System.out.println("in: " + codeword + " st: " + s);
            val = s;
            encodedMessage.append(val);
            //System.out.println("out " + val);
        }
        out.close();
        //System.out.println("Encoded message: " + encodedMessage.toString());
    }

    public static void untarFile(String tarFilePath) throws IOException {
        File tarFile = new File(tarFilePath);
        if (!tarFile.exists()) {
            throw new IOException("The tar file does not exist");
        }
    
        if (tarFile.isDirectory()) {
            throw new IOException("The tar file is a directory");
        }
    
        BinaryIn in = null;
        BinaryOut out = null;
    
        char sep = (char) 255;  // all ones 11111111
    
        try {
            in = new BinaryIn(tarFilePath);
    
            // Get the directory path from the archive file path
            String dirPath = tarFilePath.substring(0, tarFilePath.lastIndexOf('/') + 1);
    
            while (!in.isEmpty()) {
                int filenamesize = in.readInt();
                if (in.readChar() != sep) throw new IOException("Invalid archive format");
    
                String filename = "";
                for (int i = 0; i < filenamesize; i++) {
                    char c = in.readChar();
                    filename += c;
                }
    
                if (in.readChar() != sep) throw new IOException("Invalid archive format");
    
                long filesize = in.readLong();
                if (in.readChar() != sep) throw new IOException("Invalid archive format");
    
                System.out.println("Extracting file: " + filename + " (" + filesize + ").");
    
                // Remove the directory path from the filename
                filename = filename.substring(filename.lastIndexOf('/') + 1);
    
                // Prepend the directory path to the filename
                out = new BinaryOut(dirPath + filename);
    
                for (int i = 0; i < filesize; i++) {
                    char c = in.readChar();
                    out.write(c);
                }
    
                if (out != null) {
                    out.close();
                }
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //System.out.println("main called with " + Arrays.toString(args));
        if(args.length >=1){
            for (String compressedFilePath: args){
                String decompressedFile=compressedFilePath.replace(".hh","").replace(".ll","");
                if(compressedFilePath.endsWith(".hh")){
                    DecompressHuffman(compressedFilePath, decompressedFile);
                }
                else if(compressedFilePath.endsWith(".ll")){
                    DecompressLZW(compressedFilePath, decompressedFile);
                }
                else if(compressedFilePath.endsWith(".zl")){
                    untarFile(compressedFilePath);
                }
                else{
                    System.out.println("Unsupported file extension");
                }
            }
        }
        else{
            System.out.println("no command line arguments provided");
        }
        
    }
}
