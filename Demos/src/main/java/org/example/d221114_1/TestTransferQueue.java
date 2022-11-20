package org.example.d221114_1;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class TestTransferQueue {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        TransferQueue<String> transferQueue=new LinkedTransferQueue<>();
        Thread prodcutor=new Thread(()->{
            int i=0;
            while(i++<10){
                String ele="ele "+i;
                System.out.println("Start thranser "+ele);
                try {
                    transferQueue.transfer(ele);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Done transfer "+ele);
            }
        });

        Thread consumer=new Thread(()->{
            int i=0;
            while (i++<10){
                try {
                    String ele= transferQueue.take();
                    System.out.println("Get ele "+ele);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        consumer.start();
        prodcutor.start();
        try {
            consumer.join();
            prodcutor.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
