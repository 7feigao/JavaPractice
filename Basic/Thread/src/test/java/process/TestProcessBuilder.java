package process;

import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

public class TestProcessBuilder {
    @Test
    public void testProcessBuilder() {
        ProcessBuilder processBuilder = new ProcessBuilder("echo", "hello");
        try {
            Process process = processBuilder.start();
            Scanner scanner = new Scanner(process.getInputStream());
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
