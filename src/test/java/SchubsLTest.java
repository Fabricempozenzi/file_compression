import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SchubsLTest{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testEmptyFile() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "empty.txt"});
        // Check that the compressed file exists and is empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "empty.txt.ll")));
        assertEquals(1, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "empty.txt.ll")));
    }

    @Test
    public void testSingleCharacterFile() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "singlechar.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "singlechar.txt.ll")));
        assertNotEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "singlechar.txt.ll")));
    }

    @Test
    public void testMultipleCharacterFile() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "multiplechars.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "multiplechars.txt.ll")));
        assertNotEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "multiplechars.txt.ll")));
    }

    @Test
    public void testCompressedFileAlreadyExists() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "compressed.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "compressed.txt.ll")));
        long sizeBefore = Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "compressed.txt.ll"));

        // Compress the file again
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "compressed.txt"});

        // Check that the size of the compressed file hasn't changed
        assertEquals(sizeBefore, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "compressed.txt.ll")));
    }

    @Test
    public void testMultipleFiles() throws IOException {
        // Array of file names
        String[] fileNames = {"src" + File.separator + "test" + File.separator + "resources" + File.separator + "multiplechars.txt","src" + File.separator + "test" + File.separator + "resources" + File.separator + "singlechar.txt" };

        // Compress the files
        for (String fileName : fileNames) {
            SchubsL.main(new String[]{fileName});
        }

        // Check that the compressed files exist and are not empty
        for (String fileName : fileNames) {
            Path compressedFilePath = Paths.get(fileName + ".ll");
            //System.out.println("Checking file: " + compressedFilePath);
            assertTrue(Files.exists(compressedFilePath));
            if (!fileName.endsWith("empty.txt")) {
                assertNotEquals(0, Files.size(compressedFilePath));
            } else {
                assertEquals(0, Files.size(compressedFilePath));
            }
        }
    }

    @Test
    public void testLargeFile() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "large.txt"});

        // Check that the compressed file exists and is smaller than the original file
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "large.txt.ll")));
        assertTrue(Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "large.txt.ll")) < Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "large.txt")));
    }

    @Test
    public void testOneReallyLongWordWithNoSpaces() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "longword.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "longword.txt.ll")));
        assertNotEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "longword.txt.ll")));
    }

    @Test
    public void testOnlyLowercase() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "lowercase.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "lowercase.txt.ll")));
        assertNotEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "lowercase.txt.ll")));
    }
    @Test
    public void testOnlyUppercase() throws IOException {
        // Compress the file
        SchubsL.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "uppercase.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "uppercase.txt.ll")));
        assertNotEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "uppercase.txt.ll")));
    }

}