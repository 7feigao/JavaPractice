package blockingqueue;

import org.junit.Test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayQueue {
    class DelayObj implements Delayed{

        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }
    @Test
    public void testDelayQueue(){
//        DelayQueue<String> delayQueue=new DelayQueue<String>();
    }
}
