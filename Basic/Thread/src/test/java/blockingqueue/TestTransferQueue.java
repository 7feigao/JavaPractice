package blockingqueue;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class TestTransferQueue {
    @Test
    public void testLinkedTransferQueue(){
        LinkedTransferQueue<String> linkedBlockingQueue = new LinkedTransferQueue<>();
        Thread productor = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                String uuid = UUID.randomUUID().toString();
                System.out.println(LocalDateTime.now() + " Star to put element: " + uuid);
                try {
                    linkedBlockingQueue.transfer(uuid);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalDateTime.now() + " Done to put element: " + uuid);
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("productor is stopped!");
        });

        Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(LocalDateTime.now() + " Star to take element");
                String uuid = "";
                try {
                    uuid = linkedBlockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Done to take element: " + uuid);
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("consumer is stopped!");
        });
        consumer.start();
        productor.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.interrupt();
        productor.interrupt();
    }
}
