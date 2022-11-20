package org.example.d221119;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TestCompeltableFeature{
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(30);
        List<String> results= Collections.synchronizedList(new LinkedList<>());
        int capacity=100;
        AtomicLong finishNum=new AtomicLong(0);
        for (int i = 0; i < capacity; i++) {
            CompletableFuture<String> res=CompletableFuture.supplyAsync(()->{
                try {
                    TimeUnit.SECONDS.sleep((int)(Math.random()*10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if((int)(Math.random()*10)==5){
                    throw new RuntimeException(Thread.currentThread().getName()+ " Get five");
                }
                String r=Thread.currentThread().getName()+" : "+new Date().toString();
                System.out.println(r);
                return r;
            },executorService);
            res.whenComplete((result,exception)->{
                finishNum.addAndGet(1);
                if(Objects.isNull(exception)){
                    results.add(result);
                }else{
                    System.out.println(exception.getMessage());
                }
            });
            res.thenApply((result)->{
                System.out.println("then Apply "+result);
                return new Date();
            }).thenAccept((result)->{
                System.out.println("then accept "+result.toString());
            });
//            res.thenCompose((result)->{
//              return "";
//            })
            res.handle((result,exception)->{
                if(exception==null)
                System.out.println("handle "+result);
                else{
                    System.out.println("handle get exception: "+exception);
                    try {
                        throw exception;
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
                return new Date();
            }).whenComplete((result,exception)->{
                if(exception==null){
                    System.out.println("whenComplete "+result);
                }else{
                    System.out.println("When complete exception"+exception.getMessage());
                }
            });
        }
        executorService.shutdown();
        while (finishNum.get()!=capacity){
            System.out.println("finished num: "+results.size());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println();

    }
}
