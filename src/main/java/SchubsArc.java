import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Program: SchubsArch
 *  Author : Fabrice Mpozenzi
 *  Date   : 05/06/2024
 *  Course : CS375 Software Engineering II
 *  Compile: mvn compile
 *  Execute: java -cp target/classes SchubsArc src/test/resources/blee  src/test/resources/*.txt   
 */

public class SchubsArc {
    public static void main(String[] args) throws IOException {
        if(args.length < 2){
            throw new IllegalArgumentException("wrong number of arguments");
        }

        BinaryOut out = null;
        char separator = (char) 255;  // all ones 11111111

        // archive file is at args[0]
        // layout: file-name-length, separator, filename, file-size, file
        File archiveFile = new File(args[0] + ".zl");
        if(archiveFile.exists()){
            throw new IOException("Archive file already exists");
        }
        else{
            System.out.println("Archived Successfully");
        }

        try {
            out = new BinaryOut(args[0] + ".zl");

            // notice the input files start at arg[1], not arg[0]
            for (int i = 1; i < args.length; i++) {
                File in1 = new File(args[i]);
                if (!in1.exists() || !in1.isFile()) {
                    throw new IOException("Input is not a file");
                }

                // Compress the file using SchubsL
                String compressedFileName = args[i] + ".ll";
                SchubsL.compress(args[i], compressedFileName);

                File compressedFile = new File(compressedFileName);
                long filesize = compressedFile.length();
                int filenamesize = compressedFileName.length();

                out.write(filenamesize);
                out.write(separator);

                out.write(compressedFileName);
                out.write(separator);

                // write the file size
                out.write(filesize);
                out.write(separator);

                // now copy the input file to the output, one character at a time
                BinaryIn bin1 = new BinaryIn(compressedFileName);
                while (!bin1.isEmpty()) {
                    char c = bin1.readChar();
                    out.write(c);
                }
                Path path = Paths.get(compressedFileName);
                Files.delete(path);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}