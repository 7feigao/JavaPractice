package org.example.d221119;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestProcess {
    public static void main(String[] args) throws IOException, InterruptedException {
        File outputFile=File.createTempFile("test_process_output",".env");
        System.out.println("temp file: "+outputFile.getAbsolutePath());
        ProcessBuilder processBuilder = new ProcessBuilder("printenv");
//        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT); // redirect to stand output
        processBuilder.redirectOutput(ProcessBuilder.Redirect.to(outputFile)); // redirect to file
        processBuilder.environment().clear();//inert env vars from current env by default
        processBuilder.environment().put("Test","test");

//        processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        Process process=processBuilder.start();
        ;
        process.waitFor();;
        Files.readAllLines(outputFile.toPath()).stream().forEach(System.out::println);

        ProcessBuilder readFileProcess=new ProcessBuilder("awk","{print $0}");
        readFileProcess.redirectInput(outputFile);
        readFileProcess.redirectError(ProcessBuilder.Redirect.INHERIT);
        readFileProcess.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        readFileProcess.start().waitFor();
    }

}
