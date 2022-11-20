package org.example.d221111_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class D221111_2_LongAdder {


    public static void main(String[] args) {
        LongAdder longAdder=new LongAdder();
        List<TestLongAdder> testLongAdderList=new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            testLongAdderList.add(new TestLongAdder(longAdder,10));
        }
        testLongAdderList.stream().forEach(Thread::start);
        testLongAdderList.stream().forEach(testLongAdder -> {
            try {
                testLongAdder.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(longAdder.sum());
    }
}

class TestLongAdder extends Thread{
    private LongAdder longAdder;
    private int sumSize;


    public TestLongAdder(LongAdder longAdder, int sumSize) {
        this.longAdder = longAdder;
        this.sumSize = sumSize;
    }

    public void run(){
    while(sumSize-->0){
        longAdder.add(10);
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread "+Thread.currentThread().getName()+" num:"+longAdder.toString());
    }
}
}