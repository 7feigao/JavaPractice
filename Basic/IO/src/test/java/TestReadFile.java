import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestReadFile {
    @Test
    public void testReadFile(){
        try {
            String s= Files.readAllLines(Paths.get("test.txt")).stream().collect(Collectors.joining(", "));
            Assert.assertEquals("hello, world!",s);
            Files.lines(Paths.get("test.txt")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
