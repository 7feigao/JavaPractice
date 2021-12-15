import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestCreateDirFile {

    @Test
    public void testCreateDirFiles(){
        try {
            Files.createDirectories(Paths.get("./test1/test2/test3"));
            Assert.assertThrows(NoSuchFileException.class,()->Files.createDirectory(Paths.get("./test4/test2/test3")));
            Files.createFile(Paths.get("./test.txt"));//create an empty file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testCreateTempDirFiles(){
        try {
            Path newTempFile=Files.createTempFile("testPrefix","testSuffix");
            System.out.println(newTempFile.toAbsolutePath().normalize().toString());
            Path newTempDir=Files.createTempDirectory("testPrefix");
            System.out.println(newTempDir.toAbsolutePath().normalize().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
