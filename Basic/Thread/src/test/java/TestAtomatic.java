import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class TestAtomatic {
    @Test
    public void testAtmoicInt(){
        AtomicInteger atomicInteger=new AtomicInteger(0);
        atomicInteger.addAndGet(1);
        System.out.println(atomicInteger.get());
    }
    @Test
    public void testLongAdder(){
        /*better performance when add operation is used by a large number of threads*/
        LongAdder longAdder=new LongAdder();
        longAdder.add(10);
        System.out.println(longAdder.sum());
    }
}
