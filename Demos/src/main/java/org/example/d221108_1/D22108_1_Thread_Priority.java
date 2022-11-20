package org.example.d221108_1;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class D22108_1_Thread_Priority {
    public static void main(String[] args) throws InterruptedException {
        int i=Thread.MIN_PRIORITY;
        List<TestThreadPriority> list=new ArrayList<>();
        while(i<= Thread.MAX_PRIORITY){
            AtomicLong atomicLong=new AtomicLong(0);
            list.add(new TestThreadPriority(i,atomicLong));
            i++;
        }
        list.stream().forEach(rec->rec.start());
        TimeUnit.SECONDS.sleep(20);
        list.stream().forEach(rec->rec.interrupt());
        list.stream().forEach(rec-> {
            try {
                rec.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        list.stream().forEachOrdered(rec->{
            System.out.println(String.format("Pri: %d Sum:%d",rec.getPriority(),rec.getAtomicLong().get()));
        });
    }
}

class TestThreadPriority extends Thread{
    private int priority;
    private AtomicLong atomicLong;

    public TestThreadPriority(int priority, AtomicLong atomicLong) {
        this.priority = priority;
        this.atomicLong = atomicLong;
        this.setPriority(priority);
    }

    public void run(){
        int i=0;


        while(!Thread.interrupted()){
            atomicLong.addAndGet(1);

        }

    }


    public AtomicLong getAtomicLong() {
        return atomicLong;
    }
}
