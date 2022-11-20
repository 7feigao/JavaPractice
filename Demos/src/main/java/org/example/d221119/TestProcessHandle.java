package org.example.d221119;


import java.io.File;

public class TestProcessHandle {
    public static void main(String[] args) {
        ProcessHandle.allProcesses().forEach(processhandle->{
            System.out.println();
        });
    }
}
