import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Program: SchubsL
 *  Author : Fabrice Mpozenzi
 *  Date   : 05/06/2024
 *  Course : CS375 Software Engineering II
 *  Compile: mvn compile
 *  Execute: java -cp target/classes SchubsL src/test/resources/*.txt  
 */

public class SchubsL {
    private static final int R = 128;        // number of input chars
    private static final int L = 256;       // number of codewords = 2^W
    private static final int W = 8;         // codeword width

    public static void compress(String filePath, String outPath) throws IOException { 
        
        String input = new String(Files.readAllBytes(Paths.get(filePath)));
        TST<Integer> st = new TST<Integer>();
        BinaryStdOut.setOutputFile(outPath);
        /*if(input.isEmpty()){
            throw new RuntimeException("input file is empty");
        }*/

        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            if (s != null && st.contains(s)) {
                BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
                
                int t = s.length();
                if (t < input.length() && code < L) {
                    // Add s to symbol table.
                    st.put(input.substring(0, t + 1),code++);
                }   
                input = input.substring(t);            // Scan past s in input.
            } else {
                break;
            }
        }
        
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void main(String[] args) throws IOException {
        if(args.length >=1){
            for (String uncompressedFileName : args){
                String compressedFileName=uncompressedFileName + ".ll";
                if(Files.exists(Paths.get(compressedFileName))){
                    System.out.println( "Compressed file already exists");
                    return;
                }
                BinaryStdOut.setOutputFile(compressedFileName);
                compress(uncompressedFileName,compressedFileName);
                System.out.println("File (" + Paths.get(uncompressedFileName).getFileName().toString()+") " + "Compressed Successfully");
                BinaryStdOut.close();
            }
        }
        else{
            System.out.println("no command line arguments provided");
        }
    }
}