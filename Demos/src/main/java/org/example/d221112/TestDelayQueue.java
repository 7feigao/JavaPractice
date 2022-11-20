package org.example.d221112;

import parallel.Consumer;
import parallel.FProductor;
import parallel.Productor;

import java.io.*;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TestDelayQueue {

    public static void main(String[] args) {
        TestProductConsumer.test();
        String filePath="/Users/qifeigao/repos/JavaPractice/Demos/src/main/java/org/example/d221109_1/D221109_1_Reetrant_Lock.java";
        FProductor<String> fproductor=(blockingQueue)->{
            File file=new File(filePath);
            try {
                BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
                String line=null;
                while ((line=bufferedReader.readLine())!=null){
                    blockingQueue.put(line);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Productor productor=fproductor.create();
        productor.start();

        Consumer<String,Integer> consumer=new Consumer<String, Integer>(productor,new AtomicInteger(20)) {
            @Override
            public Integer apply(String ele) {
                int i=0;
                while(i++<100000L);
                return ele.length();
            }
        };
        consumer.start();
        AtomicLong atomicLong=new AtomicLong();
        consumer.collect((num)->{
            atomicLong.addAndGet(num);
        });

        System.out.println("Total chars:"+atomicLong.get());


    }
}

class TestProductConsumer {
    public static void test(){
        FProductor<Date> dateFProductor=(BlockingQueue<Date> blockingQueue)->{
            int i=0;
            while(i++<1000){
                try {
                    blockingQueue.put(new Date());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

//       Productor<Date> dateProductor=dateFProductor.create(new ArrayBlockingQueue<>(10));
        Productor<Date> dateProductor=dateFProductor.create(new PriorityBlockingQueue<>(10));


        dateProductor.start();

       Consumer<Date,Long> consumer=new Consumer<Date, Long>(dateProductor,new AtomicInteger(20)) {
           @Override
           public Long apply(Date ele) {
               long delay=(new Date().getTime()-ele.getTime());
               try {
                   TimeUnit.SECONDS.sleep(1);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               System.out.println(Thread.currentThread().getName()+" Delay: " +delay);
               return delay;
           }
       };
       consumer.start();

       consumer.collect((delay)->{
           System.out.println(Thread.currentThread().getName()+" collect Delay: " +delay);
       });

    }
}



