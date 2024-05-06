import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SchubsHTest{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testEmptyFile() throws IOException {
        // Compress the file
        SchubsH.main(new String[]{ "src" + File.separator + "test" + File.separator + "resources" + File.separator + "empty.txt" });
        // Check that the compressed file exists and is empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "empty.txt.hh")));
        assertEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "empty.txt.hh")));
    }

    @Test
    public void testSingleCharacterFile() throws IOException {
        // Compress the file
        SchubsH.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "singlechar.txt"});
        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "singlechar.txt.hh")));
        assertNotEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "singlechar.txt.hh")));
    }

    @Test
    public void testMultipleCharacterFile() throws IOException {
        // Compress the file
        SchubsH.main(new String[]{"src" + File.separator + "test" + File.separator + "resources" + File.separator + "multiplechars.txt"});
        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "multiplechars.txt.hh")));
        assertNotEquals(0, Files.size(Paths.get("src" + File.separator + "test" + File.separator + "resources" + File.separator + "multiplechars.txt.hh")));
    }

    @Test
    public void testCompressedFileAlreadyExists() throws IOException {
        // Compress the file
        SchubsH.main(new String[]{"src/test/resources/compressed.txt"});
        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src/test/resources/compressed.txt.hh")));
        long sizeBefore = Files.size(Paths.get("src/test/resources/compressed.txt.hh"));
        // Compress the file again
        SchubsH.main(new String[]{"src/test/resources/compressed.txt"});
        // Check that the size of the compressed file hasn't changed
        assertEquals(sizeBefore, Files.size(Paths.get("src/test/resources/compressed.txt.hh")));
    }

    @Test
    public void testMultipleFiles() throws IOException {
        // Array of file names
        String[] fileNames = {"src/test/resources/multiplechars.txt","src/test/resources/singlechar.txt" };

        // Compress the files
        for (String fileName : fileNames) {
            SchubsH.main(new String[]{fileName});
        }
        // Check that the compressed files exist and are not empty
        for (String fileName : fileNames) {
            Path compressedFilePath = Paths.get(fileName + ".hh");
            System.out.println("Checking file: " + compressedFilePath);
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
        SchubsH.main(new String[]{"src/test/resources/large.txt"});

        // Check that the compressed file exists and is smaller than the original file
        assertTrue(Files.exists(Paths.get("src/test/resources/large.txt.hh")));
        assertTrue(Files.size(Paths.get("src/test/resources/large.txt.hh")) < Files.size(Paths.get("src/test/resources/large.txt")));
    }

    @Test
    public void testOneReallyLongWordWithNoSpaces() throws IOException {
        // Compress the file
        SchubsH.main(new String[]{"src/test/resources/longword.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src/test/resources/longword.txt.hh")));
        assertNotEquals(0, Files.size(Paths.get("src/test/resources/longword.txt.hh")));
    }

    @Test
    public void testOnlyLowercase() throws IOException {
        // Compress the file
        SchubsH.main(new String[]{"src/test/resources/lowercase.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src/test/resources/lowercase.txt.hh")));
        assertNotEquals(0, Files.size(Paths.get("src/test/resources/lowercase.txt.hh")));
    }
    @Test
    public void testOnlyUppercase() throws IOException {
        // Compress the file
        SchubsH.main(new String[]{"src/test/resources/uppercase.txt"});

        // Check that the compressed file exists and is not empty
        assertTrue(Files.exists(Paths.get("src/test/resources/uppercase.txt.hh")));
        assertNotEquals(0, Files.size(Paths.get("src/test/resources/uppercase.txt.hh")));
    }

    

}