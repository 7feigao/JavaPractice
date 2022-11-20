package org.example.d221110_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class D221110_1_Synchronized {
    public static void main(String[] args) {
        TestCondtionObject testCondtionObject = new TestCondtionObject();
        List<Thread> consumerThread = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                int count = 10;
                while (count-- > 0) {
                    testCondtionObject.consume(finalI);
                }
            });
            consumerThread.add(thread);
        }
        Thread productThread = new Thread(() -> {
            int amount = 1000;
            while (amount-- > 0) {
                Double v = Math.random() * 10;
                testCondtionObject.product(v.intValue());
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        consumerThread.stream().forEachOrdered(Thread::start);
        productThread.start();
        try {
            productThread.join();
            consumerThread.stream().forEachOrdered(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

class TestCondtionObject {
    private int amount = 0;

    public TestCondtionObject() {
    }

    public synchronized void consume(int num) {
        while (amount < num) {
            System.out.println(Thread.currentThread().getName() + ": need " + num + ", now have " + amount + ", waiting!");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        amount -= num;
        System.out.println(Thread.currentThread().getName() + " consumed " + num + ", amount left: " + amount);

    }

    public synchronized void product(int num) {
        try {
            amount += num;
            System.out.println(Thread.currentThread().getName() + " add " + num + ", amount now: " + amount);
            notifyAll();
        } finally {
        }

    }
}