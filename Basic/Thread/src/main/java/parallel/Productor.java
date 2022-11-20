package parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Productor<E> {
    AtomicBoolean isDone=new AtomicBoolean(false);
    BlockingQueue<E> resultQueue;

    public Productor(BlockingQueue<E> blockingQueue) {
        this.resultQueue = blockingQueue;
    }

    public Productor(){
        this(new LinkedBlockingQueue<>(10));
    }

    public void start(){
        AtomicInteger aliveThreadNum=new AtomicInteger(1);
        Thread thread=new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" started!");
            try {
                this.product(this.resultQueue);
            }finally {
                aliveThreadNum.addAndGet(-1);
            }
        });
        thread.start();
        Thread monitorThread=new Thread(()->{
            try {
                while(aliveThreadNum.get()!=0){
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                this.isTaskDone().set(true);
            }
        });
        monitorThread.start();
    }

    public abstract void product(BlockingQueue<E> blockingQueue);

    public  BlockingQueue<E> getProductorQueue(){
        return this.resultQueue;
    }
    protected   AtomicBoolean isTaskDone(){
        return isDone;
    }
    public boolean isDone(){
        return this.isTaskDone().get();
    }
}

