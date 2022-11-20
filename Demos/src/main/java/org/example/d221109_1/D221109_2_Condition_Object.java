package org.example.d221109_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class D221109_2_Condition_Object {
    public static void main(String[] args) {
        TestCondtionObject testCondtionObject=new TestCondtionObject();
        List<Thread> consumerThread=new ArrayList<>(10);
        for (int i=0;i<10;i++){
            int finalI = i;
            Thread thread=new Thread(()->{
               int count=10;
               while (count-->0){
                   testCondtionObject.consume(finalI);
               }
            });
            consumerThread.add(thread);
        }
        Thread productThread=new Thread(()->{
            int amount=1000;
            while (amount-->0){
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

class TestCondtionObject{
    private int amount=0;
    Condition condition;
    Lock lock;

    public TestCondtionObject() {
        lock=new ReentrantLock();
        condition=lock.newCondition();
    }
    public void consume(int num){
        lock.lock();
        try {
            while (amount < num) {
                System.out.println(Thread.currentThread().getName()+": need "+num+", now have "+amount+", waiting!");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            amount-=num;
            System.out.println(Thread.currentThread().getName()+" consumed "+num+", amount left: "+amount);
        }finally {
            lock.unlock();
        }
    }
    public void product(int num){
        lock.lock();
        try {
            amount+=num;
            System.out.println(Thread.currentThread().getName()+" add "+num+ ", amount now: "+amount);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
}