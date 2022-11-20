package org.example.d221108_1;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class D221108_1_Thread_Interrupt {
    public static void main(String[] args) throws InterruptedException {
        testInterrupted();
        testIsInterrupted();

    }

    public static void testIsInterrupted() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                int i = 1;
                BigInteger bigInteger = new BigInteger("1");
                while (i++ < 100000) {
                    bigInteger = bigInteger.multiply(BigInteger.valueOf(i));
                }
                System.out.println("");
            }
            System.out.println("Interrupted");
            System.out.println("Current interrupt status: " + Thread.currentThread().isInterrupted());
        });
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        thread.interrupt();
        TimeUnit.SECONDS.sleep(2);
        thread.join();
    }

    public static void testInterrupted() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                int i = 1;
                BigInteger bigInteger = new BigInteger("1");
                while (i++ < 100000) {
                    bigInteger = bigInteger.multiply(BigInteger.valueOf(i));
                }
                System.out.println("");
            }
            System.out.println("Interrupted");
            System.out.println("Current interrupt status: " + Thread.currentThread().isInterrupted());
        });
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        thread.interrupt();
        TimeUnit.SECONDS.sleep(2);
        thread.join();
    }
}
