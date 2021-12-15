import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Locale;

public class TestDaemonThread {
    @Test
    public void testDaemonThread(){
        Runnable runnable=()->{
            while (true){
                System.out.println(LocalDateTime.now().toString());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread=new Thread(runnable);
        thread.setDaemon(true);//will auto stop when all other no daemon thread stopped.
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
