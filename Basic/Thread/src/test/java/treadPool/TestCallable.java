package treadPool;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCallable {
    @Test
    public void testCallable(){
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName()+": "+Math.random();
            }
        };
//        FutureTask<Double> futureTask=new FutureTask<>(callable);
       List<String> res= IntStream.range(0, 100).parallel()
               .mapToObj(rec -> new FutureTask(callable))
               .peek(futureTask1 -> new Thread(futureTask1).start())
               .map(rec -> {
                    try {

                        return (String)rec.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                 })
               .filter(Objects::nonNull).peek(System.out::println).collect(Collectors.toList());
    }
}
