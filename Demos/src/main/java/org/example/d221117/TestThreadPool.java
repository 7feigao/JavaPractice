package org.example.d221117;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testThreadPool(Executors.newCachedThreadPool());
//        testThreadPool(Executors.newFixedThreadPool(32));
//        testThreadPool(Executors.newSingleThreadExecutor());
            testScheduleThreadPool(null);
    }
    public static void testThreadPool(ExecutorService executorService) throws ExecutionException, InterruptedException {
        int i=100;
        List<Future<String>> futures=new ArrayList<>(i);
        Thread.sleep(10000);
        while (i-->0){
            Callable<String> callable=()->{
                double v = Math.random() * 1000_0000_000L+5000;
                double sum=0;
                while(v-->0){
                    sum+=v;
                }
                TimeUnit.MILLISECONDS.sleep(1000);
                return new Date().toString()+sum;
            };
            Future<String> future = executorService.submit(callable);
            futures.add(future);
        }
        executorService.shutdown();
        int finishedTasks=0;
        while (finishedTasks<i){
            for(Future future:futures){
                if(future.isDone()){
                    System.out.println(future.get());
                    finishedTasks++;
                }
            }
        }
    }
    public static void testScheduleThreadPool(ExecutorService executorService) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " date: " + new Date());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        System.out.println("init date: " + new Date().toString());
        scheduledExecutorService.schedule(runnable, 10, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(runnable,5,2,TimeUnit.SECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(runnable,5,2,TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(1000);
        scheduledExecutorService.shutdown();

    }
}
