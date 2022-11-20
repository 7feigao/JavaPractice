package parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public abstract class Consumer<E, R> extends Productor<R> {
    AtomicInteger threadNum = new AtomicInteger(1);
    Productor<E> productor;

    public Consumer<E, R> setThreadNum(int threadNum) {
        this.threadNum.set(threadNum);
        return this;
    }

    public Consumer(Productor<E> productor,AtomicInteger threadNum, BlockingQueue<R> resultQueue) {
        this.threadNum = threadNum;
        this.resultQueue = resultQueue;
        this.productor=productor;
    }

    public Consumer(Productor<E> productor,AtomicInteger threadNum) {
        this(productor,threadNum, new LinkedBlockingQueue<>(1));
    }

    public AtomicInteger getThreadNum() {
        return this.threadNum;
    }

    public abstract R apply(E ele);

    public void start() {
        int ix = 0;
        List<Thread> threadList=new ArrayList<>(this.getThreadNum().get());
        AtomicInteger aliveThreadNum=new AtomicInteger(this.getThreadNum().get());
        while (ix++ < this.getThreadNum().get()) {
            threadList.add(new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+" started");
                try {
                 this.product(this.resultQueue);
                }
                finally {
                    aliveThreadNum.addAndGet(-1);
                }
            }));
        }
        threadList.stream().forEach(Thread::start);
        Thread monitorThread=new Thread(()->{
            while(aliveThreadNum.get()!=0){
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.isTaskDone().set(true);
        });
        monitorThread.start();
    }



    @Override
    public final void product(BlockingQueue<R> blockingQueue) {
        E ele = null;
        BlockingQueue<E> productorQueue=productor.getProductorQueue();
        try {
            while ((ele = productorQueue.poll(1, TimeUnit.SECONDS))!=null || (!productor.isTaskDone().get())) {
                if (Objects.nonNull(ele)) {
                    R res = this.apply(ele);
                    blockingQueue.put(res);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void collect(java.util.function.Consumer<R> consumer){
        while(!this.isDone()){
            R res= null;
            try {
                res = this.getProductorQueue().poll(100, TimeUnit.MILLISECONDS);
                if(Objects.nonNull(res)){
                    consumer.accept(res);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
