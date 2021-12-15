import org.junit.Test;

import java.util.stream.IntStream;

public class TestRunnable {
    class Bank {
        float maxAmount = 0;
    }

    @Test
    public void testRunnable() {
        Bank bank = new Bank();
        Runnable runnable = () -> {
            for (int i = 0; i < 1000000; i++) {
                bank.maxAmount += 1;
//                try {
//                    Thread.sleep((long) Math.random() * 1000);
                    System.out.println(Thread.currentThread().getId());
//                } /*catch (InterruptedException e) {
//                    e.printStackTrace();
//                }*/
            }
        };
        IntStream.range(0,16).parallel().mapToObj(rec->new Thread(runnable)).peek(Thread::start).forEach(rec->{
            try {
                rec.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(bank.maxAmount);

    }
}
