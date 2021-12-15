import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPath {
    @Test
    public void testPath() {
        Path rootPath = Paths.get("/hello");
        System.out.println(rootPath);
        System.out.println(rootPath.resolve("test1/test2"));
        System.out.println(rootPath.resolve("/test1/test2"));
        /**
         * \hello
         * \hello\test1\test2
         * \test1\test2*/
        Path test2Path = rootPath.resolve(Paths.get("test1/test3"));
        System.out.println(test2Path);
        System.out.println(test2Path.resolveSibling("test5"));
        /**
         * \hello\test1\test3
         * \hello\test1\test5*/
        System.out.println(test2Path.relativize(rootPath));
        /**
         * ..\..*/
        Path testNormalize = Paths.get("./test1/../fdasf/./fds/");
        System.out.println(testNormalize);
        System.out.println(testNormalize.normalize());
        /*
        * .\test1\..\fdasf\.\fds
fdasf\fds*/
        System.out.println(testNormalize.toAbsolutePath());
        /**
         * F:\repos\JavaPractice\Basic\IO\.\test1\..\fdasf\.\fds*/
    }
}
