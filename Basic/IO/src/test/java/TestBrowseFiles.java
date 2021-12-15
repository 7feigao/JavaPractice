import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestBrowseFiles {
    @Test
    public void testBrowseDirs(){
        try {
            Stream<Path> pathStream=Files.list(Paths.get("./"));
            pathStream.forEach(rec-> System.out.println(rec.toAbsolutePath().normalize()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testBrowserDirsRecusive(){
        try {
//browse all files and dirs in dfs way
            Stream<Path> pathStream=Files.walk(Paths.get("./"));
            pathStream.forEach(rec-> System.out.println(rec.toAbsolutePath().normalize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
