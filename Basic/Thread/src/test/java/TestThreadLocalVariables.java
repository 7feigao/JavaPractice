import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestThreadLocalVariables {
    @Test
    public void testThreadLocalVariables(){
        Runnable runnable=()->{
            ThreadLocal<String> threadLocal=ThreadLocal.withInitial(()->Thread.currentThread().getName());
            System.out.println(threadLocal.get());
        };
        IntStream.range(0,30).mapToObj(rec->new Thread(runnable)).peek(Thread::start).collect(Collectors.toList()).forEach(thread->{
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    @Test
    public void testThreadLocalRandom(){
        Runnable runnable=()->{
            System.out.println(ThreadLocalRandom.current().nextInt(30));
        };
        IntStream.range(0,30).mapToObj(rec->new Thread(runnable)).peek(Thread::start).collect(Collectors.toList()).forEach(thread->{
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
