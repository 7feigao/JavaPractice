package org.example.d221107_1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class D221107_1_Thread_Exception {
    public static void main(String[] args) {
        ThreadExceptionWithNoHandler.main();
        ThreadExceptionWithHandler.main();
    }
}

class ThreadExceptionWithNoHandler {
    public static void main() {
        Runnable runnable = () -> {
            int i = 0;
            while (i++ < 10) {
                System.out.println(new Date());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (5 == i) {
                    int j = 10 / (i - 5);
                    System.out.println(j);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done to run thread with no exception handler");

    }
}

class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Find error in thread: "+t.getName());
    }
}


class ThreadExceptionWithHandler {
    public static void main() {
        Runnable runnable = () -> {
            int i = 0;
            while (i++ < 10) {
                System.out.println(new Date());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (5 == i) {
                    int j = 10 / (i - 5);
                    System.out.println(j);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done to run thread with no exception handler");

    }
}