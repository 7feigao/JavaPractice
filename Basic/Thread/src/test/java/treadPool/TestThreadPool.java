package treadPool;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestThreadPool {
    Callable<String> callable=()->{
        long rand=(long)(Math.random()*1000000000);
        System.out.println(LocalDateTime.now());
        for (int i = 0; i < rand; i++) {
        }
        return Thread.currentThread().getName()+": "+rand;
    };
    @Test
    public void testCachedThreadPool(){
        ExecutorService executors=Executors.newCachedThreadPool();
        List<FutureTask<String>> futureTaskList= Stream.generate(()->new FutureTask<String>(callable)).limit(160).collect(Collectors.toList());
        futureTaskList.stream().map(Thread::new).forEach(rec->executors.submit(rec));
        futureTaskList.stream().forEach(rec->{
            try {
                System.out.println(rec.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executors.shutdown();
    }
    @Test
    public void testFixedThreadPool(){
        ExecutorService executorService=Executors.newFixedThreadPool(16);
        List<FutureTask<String>> futureTaskList= Stream.generate(()->new FutureTask<String>(callable)).limit(160).collect(Collectors.toList());
        futureTaskList.stream().map(Thread::new).forEach(rec->executorService.submit(rec));
        futureTaskList.stream().forEach(rec->{
            try {
                System.out.println(rec.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
    @Test
    public void testSingleThreadExecutor(){
        ExecutorService executorService=Executors.newSingleThreadExecutor();
        List<FutureTask<String>> futureTaskList= Stream.generate(()->new FutureTask<String>(callable)).limit(160).collect(Collectors.toList());
        futureTaskList.stream().map(Thread::new).forEach(rec->executorService.submit(rec));
        futureTaskList.stream().forEach(rec->{
            try {
                System.out.println(rec.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Test
    public void testScheduledExecutorService(){
        ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(16);
        List<FutureTask<String>> futureTaskList= Stream.generate(()->new FutureTask<String>(callable)).limit(160).collect(Collectors.toList());
        scheduledExecutorService.scheduleAtFixedRate(()->{
            System.out.println(LocalDateTime.now());
        },10,1000,TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testInvokeAll(){
        ExecutorService executorService=Executors.newFixedThreadPool(16);
        try {
            List<Future<String>> futures=executorService.invokeAll(Stream.generate(()->callable).limit(160).collect(Collectors.toList()));
        futures.forEach(rec->{
            try {
                System.out.println(rec.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
