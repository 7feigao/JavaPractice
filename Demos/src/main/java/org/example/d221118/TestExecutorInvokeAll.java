package org.example.d221118;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestExecutorInvokeAll {


    public static void main(String[] args) {
        int capacity=100;
        List<Callable<String>> callables=new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            int finalI = i;
            callables.add(()->{
                return new Date().toString()+" "+ finalI;
            });
        }
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        try {
            List<Future<String>> futures=executorService.invokeAll(callables);
            for(Future<String> future:futures){
                System.out.println(future.get());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
    }

}
