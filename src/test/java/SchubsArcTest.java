import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class SchubsArcTest {
    @Test
    public void testArchiveSingleFile() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/archive1.zl"));
        SchubsArc.main(new String[]{"src/test/resources/archive1", "src/test/resources/lowercase.txt"});
        assertTrue(Files.exists(Paths.get("src/test/resources/archive1.zl")));
    }
    @Test
    public void testArchiveMultipleFiles() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/archive2.zl"));
        SchubsArc.main(new String[]{"src/test/resources/archive2", "src/test/resources/lowercase.txt", "src/test/resources/uppercase.txt"});
        assertTrue(Files.exists(Paths.get("src/test/resources/archive2.zl")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArchiveNoFiles() throws IOException {
        SchubsArc.main(new String[]{"src/test/resources/archive3"});
    }

    @Test(expected = IOException.class)
    public void testArchiveFileAlreadyExists() throws IOException {
        Files.createFile(Paths.get("src/test/resources/archive4.zl"));
        SchubsArc.main(new String[]{"src/test/resources/archive4", "src/test/resources/lowercase.txt"});
    }

    @Test
    public void testLargeFile() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/archiveLarge.zl"));
        SchubsArc.main(new String[]{"src/test/resources/archiveLarge", "src/test/resources/large.txt"});
        assertTrue(Files.exists(Paths.get("src/test/resources/archiveLarge.zl")));
    }

    @Test
    public void testOneLongWord() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/archiveLongWord.zl"));
        SchubsArc.main(new String[]{"src/test/resources/archiveLongWord", "src/test/resources/longword.txt"});
        assertTrue(Files.exists(Paths.get("src/test/resources/archiveLongWord.zl")));
    }

    @Test
    public void testOnlyLowercase() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/archiveLowercase.zl"));
        SchubsArc.main(new String[]{"src/test/resources/archiveLowercase", "src/test/resources/lowercase.txt"});
        assertTrue(Files.exists(Paths.get("src/test/resources/archiveLowercase.zl")));
    }

    @Test
    public void testOnlyUppercase() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/archiveUppercase.zl"));
        SchubsArc.main(new String[]{"src/test/resources/archiveUppercase", "src/test/resources/uppercase.txt"});
        assertTrue(Files.exists(Paths.get("src/test/resources/archiveUppercase.zl")));
    }

    @Test(expected = IOException.class)
    public void testNonExistentFile() throws IOException {
        SchubsArc.main(new String[]{"src/test/resources/archiveNonExistent", "src/test/resources/nonExistent.txt"});
    }

    @Test(expected = IOException.class)
    public void testDirectoryInsteadOfFile() throws IOException {
        SchubsArc.main(new String[]{"src/test/resources/archiveDirectory", "src/test/resources"});
    }

    @Test
    public void testLargeNumberOfFiles() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/archiveLargeNumber.zl"));
        String[] args = new String[102];
        args[0] = "src/test/resources/archiveLargeNumber";
        for (int i = 1; i <= 101; i++) {
            args[i] = "src/test/resources/lowercase.txt";
        }
        SchubsArc.main(args);
        assertTrue(Files.exists(Paths.get("src/test/resources/archiveLargeNumber.zl")));
    }
}