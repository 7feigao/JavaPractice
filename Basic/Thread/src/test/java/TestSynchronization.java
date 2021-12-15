import org.junit.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSynchronization {
    @Test
    public void testWithoutLock() throws InterruptedException {
        final int[] a = {100, 100};
        Runnable runnable = () -> {
            int i = 10000;
            while (i-- > 0) {
                if (Math.random() - 0.5 > 0) {
                    a[0] -= 10;
                    a[1] += 10;
                } else {
                    a[1] -= 30;
                    a[0] += 30;
                }
                if (i % 100 == 0)
                    System.out.println(a[0] + a[1]);
            }
        };
        List<Thread> threads = IntStream.range(0, 16).mapToObj(rec -> new Thread(runnable)).peek(Thread::start).collect(Collectors.toList());
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    public void testReentrantLock() throws InterruptedException {
        final int[] a = {100, 100};
        ReentrantLock reentrantLock = new ReentrantLock();
        Runnable runnable = () -> {
            int i = 10000;
            while (i-- > 0) {
                if (Math.random() - 0.5 > 0) {
                    reentrantLock.lock();
                    a[0] -= 10;
                    a[1] += 10;
                    reentrantLock.unlock();
                } else {
                    reentrantLock.lock();
                    a[1] -= 30;
                    a[0] += 30;
                    reentrantLock.unlock();
                }
                if (i % 100 == 0)
                    System.out.println(a[0] + a[1]);
            }
        };
        List<Thread> threads = IntStream.range(0, 16).mapToObj(rec -> new Thread(runnable)).peek(Thread::start).collect(Collectors.toList());
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    public void testAwaitNotify() throws InterruptedException {
        int a[] = new int[]{0};
        ReentrantLock reentrantLock = new ReentrantLock(true);//during the test, if set fair to true, consumer thread 1,3,5 will always can consume money, but 0,2,4 failed to consume money

//        ReentrantLock reentrantLock = new ReentrantLock();//during the test, if set fair as default(false), only consumer thread 5 con consume money.
        Condition condition = reentrantLock.newCondition();
        Runnable consumer = () -> {
            while (true) {
                reentrantLock.lock();
                try {
                    while (a[0] < 10) {
                        System.out.println(String.format("%s, no enough money(%d), waiting!", Thread.currentThread().getName(), a[0]));
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " get 10!");
                    a[0] -= 10;
                    condition.signalAll();
                } finally {
                    reentrantLock.unlock();
                }
            }
        };

        Runnable saver = () -> {
            while (true) {
                reentrantLock.lock();
                try {
                    while (a[0] > 0) {
                        System.out.println(String.format("%s, still enough money(%d), waiting!", Thread.currentThread().getName(), a[0]));
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    a[0] += 30;
                    System.out.println(String.format("Saved 30!"));
                    condition.signalAll();
                } finally {
                    reentrantLock.unlock();
                }
            }
        };
        List<Thread> consumerThread = IntStream.range(0, 6).mapToObj(rec -> new Thread(consumer)).peek(Thread::start).collect(Collectors.toList());
        Thread saverThread = new Thread(saver);
        saverThread.setName("Thread-saver");
        saverThread.start();
        saverThread.join();
    }
}
