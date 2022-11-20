package org.example.d221116;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class TestCopyOnWriteArrayList {
    public static void main(String[] args) throws InterruptedException {
        test();
        try {
            testNormalList();
        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }
    }

    public static void test() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        Thread productor = new Thread(() -> {
            int i = 0;
            while (i++ < 20) {
                copyOnWriteArrayList.add(i);
                System.out.println("Done to insert " + i);
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        productor.start();
        TimeUnit.SECONDS.sleep(1);
        Iterator<Integer> integerIterator = copyOnWriteArrayList.iterator();
        while (integerIterator.hasNext()) {
            System.out.println(integerIterator.next());
            TimeUnit.MILLISECONDS.sleep(300);
        }
        System.out.println("Done to iterator");
    }

    public static void testNormalList() throws InterruptedException {
        List<Integer> copyOnWriteArrayList = new LinkedList<>();
        Thread productor = new Thread(() -> {
            int i = 0;
            while (i++ < 20) {
                copyOnWriteArrayList.add(i);
                System.out.println("Done to insert " + i);
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        productor.start();
        TimeUnit.SECONDS.sleep(1);
        Iterator<Integer> integerIterator = copyOnWriteArrayList.iterator();
        while (integerIterator.hasNext()) {
            System.out.println(integerIterator.next());
            TimeUnit.MILLISECONDS.sleep(300);
        }
        System.out.println("Done to iterator");
    }
}
