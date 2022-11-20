package org.example.d221119;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestExecutorCompleteService
{
    public static void main(String[] args) {
        ExecutorService executorService1=Executors.newFixedThreadPool(30);
        ExecutorCompletionService<String> executorService=new ExecutorCompletionService<>(executorService1);
        int capacity=1000;
        List<Callable<String>> list=new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            list.add(()->{
                int timeSleep=2+(int)(Math.random()*10);
                System.out.println(Thread.currentThread().getName()+" sleep "+timeSleep);
                return ""+timeSleep;
            });
        }
        list.stream().forEach(rec->executorService.submit(rec));
        executorService1.shutdown();
        for (int i = 0; i < capacity; i++) {
            try {
                System.out.println(executorService.take().get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
