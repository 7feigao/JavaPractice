package org.example.d221116;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class TestCallable {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        Callable<String> stringCallable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                return new Date().toString();
            }
        };

        FutureTask futureTask=new FutureTask(stringCallable);

        Thread thread=new Thread(futureTask);
        thread.start();
        while(!futureTask.isDone()){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println("waiting");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
