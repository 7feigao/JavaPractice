import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TestPrintWriter {
    @Test
    public void testPrintWriter(){
        try {
            PrintWriter printWrite=new PrintWriter("test.txt", String.valueOf(StandardCharsets.UTF_8));
            printWrite.write("hello");
            printWrite.write("\n");
            printWrite.write("world!");
            printWrite.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
