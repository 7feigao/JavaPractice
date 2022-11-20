package org.example.d221118;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestExecutorInvokeAny {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();
        int capacity=100;
        List<Callable<String>> tasks=new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            tasks.add(()->{
                int sleepTime=(int)(Math.random()*30)+2;
                System.out.println(Thread.currentThread().getName()+" will sleep: "+sleepTime);
                long millSleepTime=sleepTime*1000;
                while (millSleepTime>0){
                    TimeUnit.MILLISECONDS.sleep(1);
                    millSleepTime-=1;
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println(Thread.currentThread().getName()+ " is been interrupted, should sleep "+sleepTime);
                        return null;
                    }
                }
                return ""+sleepTime;
            });
        }
        try {
            String future=executorService.invokeAny(tasks);
            System.out.println(future);
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
