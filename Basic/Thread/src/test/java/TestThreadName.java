import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestThreadName {
    @Test
    public void testThreadName() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println(String.format("id:%d\n name:%s", Thread.currentThread().getId(), Thread.currentThread().getName()));
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        thread=new Thread(runnable);
        thread.setName("namedThread");
        thread.start();
        thread.join();

        Thread thread1=new Thread(()->{
            System.out.println("hello");
        });
        thread1.start();
    }

}
