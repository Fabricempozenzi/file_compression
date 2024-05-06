import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class DeschubsTest {

    @Test
    public void testDecompressHuffman() throws IOException {
        String inPath = "src/test/resources/large.txt.hh";
        String outPath = "src/test/resources/large.txt";
        Deschubs.DecompressHuffman(inPath, outPath);
        assertTrue(new File(outPath).exists());
    }

    @Test
    public void testDecompressLZW() throws IOException {
        String inPath = "src/test/resources/lowercase.txt.ll";
        String outPath = "src/test/resources/lowercase.txt";
        Deschubs.DecompressLZW(inPath, outPath);
        assertTrue(new File(outPath).exists());
    }

    @Test
    public void testUntarFile() throws IOException {
        String inPath = "src/test/resources/archiveLarge.zl";
        Deschubs.untarFile(inPath);
        // Assuming the tar file contains a file named "test.txt"
        assertTrue(new File("src/test/resources/large.txt.ll").exists());
    }

    @Test
    public void testMainNoArgs() throws IOException {
        String[] args = {};
        Deschubs.main(args);
        assertFalse(new File("src/test/resources/test.txt").exists());
    }

    @Test
    public void testMainUnsupportedExtension() throws IOException {
        String[] args = {"src/test/resources/test.unsupported"};
        Deschubs.main(args);
        assertFalse(new File("src/test/resources/test.txt").exists());
    }

    @Test
    public void testDecompressHuffmanFileExists() throws IOException {
        String inPath = "src/test/resources/test.txt.hh";
        String outPath = "src/test/resources/test.txt";
        new File(outPath).createNewFile();
        Deschubs.DecompressHuffman(inPath, outPath);
        assertTrue(new File(outPath).exists());
        Files.delete(Paths.get(outPath));
    }

    @Test
    public void testDecompressLZWFileExists() throws IOException {
        String inPath = "src/test/resources/test.txt.ll";
        String outPath = "src/test/resources/test.txt";
        new File(outPath).createNewFile();
        Deschubs.DecompressLZW(inPath, outPath);
        assertTrue(new File(outPath).exists());
        Files.delete(Paths.get(outPath));
    }

    @Test(expected = IOException.class)
    public void testUntarFileDoesNotExist() throws IOException {
        String inPath = "src/test/resources/nonexistent.zl";
        Deschubs.untarFile(inPath);
    }

    @Test(expected = IOException.class)
    public void testUntarFileIsDirectory() throws IOException {
        String inPath = "src/test/resources/directory.zl";
        new File(inPath).mkdir();
        Deschubs.untarFile(inPath);
        Files.delete(Paths.get(inPath));
    }
}