package org.example.d221115_1;

import parallel.Consumer;
import parallel.FProductor;
import parallel.Productor;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TestPriorityQueue {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        FProductor<Integer> fProductor=(blockingQueue)->{
            int i=0;
            while(i++<100){
                int j=(int)(Math.random()*100);
                System.out.println("insert "+j);
                try {
                    blockingQueue.put(j);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Productor<Integer> productor=fProductor.create(new PriorityBlockingQueue<>());
        Consumer<Integer,Boolean> consumer=new Consumer<Integer,Boolean>(productor,new AtomicInteger(2)) {
            @Override
            public Boolean apply(Integer ele) {
                System.out.println(Thread.currentThread().getName()+": "+ele);
                return true;
            }
        };
        productor.start();
        consumer.start();

        consumer.collect((result)->{

        });
    }
}
