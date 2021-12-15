package blockingqueue;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class TestLinkedBlockingQueue {
    @Test
    public void testAddElement() {
        LinkedBlockingQueue<String> linkedNoUpperBoundBlockingQueue = new LinkedBlockingQueue<>();//you can add as many element as you want
        for (char c : "abcdefg".toCharArray()) {
            Assert.assertTrue(linkedNoUpperBoundBlockingQueue.add(c + ""));
        }
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(2);

//add
        Assert.assertTrue(linkedBlockingQueue.add("a"));
        Assert.assertTrue(linkedBlockingQueue.add("b"));
        LinkedBlockingQueue<String> finalLinkedBlockingQueue = linkedBlockingQueue;
        Assert.assertThrows(IllegalStateException.class, () -> finalLinkedBlockingQueue.add("c"));

//offer
        linkedBlockingQueue = new LinkedBlockingQueue<>(2);
        Assert.assertTrue(linkedBlockingQueue.offer("a"));
        Assert.assertTrue(linkedBlockingQueue.offer("b"));
        Assert.assertFalse(linkedBlockingQueue.offer("c"));
    }

    @Test
    public void testAddElementAndBlockWhenFull() {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(2);
        Thread thread = new Thread(() -> {
            try {
                linkedBlockingQueue.put("a");
                System.out.println("put a");
                linkedBlockingQueue.put("b");
                System.out.println("put b");
                linkedBlockingQueue.put("c");
                System.out.println("put c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
//        Assert.assertThrows(InterruptedException.class, () -> thread.interrupt());
    }

    @Test
    public void testGetElement() {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
//        peek and element
        Assert.assertThrows(NoSuchElementException.class, () -> linkedBlockingQueue.element());
        Assert.assertNull(linkedBlockingQueue.peek());
        linkedBlockingQueue.add("a");
        Assert.assertEquals("a", linkedBlockingQueue.element());
        Assert.assertEquals("a", linkedBlockingQueue.element());

        Assert.assertEquals("a", linkedBlockingQueue.peek());
        Assert.assertEquals("a", linkedBlockingQueue.peek());
//        poll and remove
        Assert.assertEquals("a", linkedBlockingQueue.poll());
        Assert.assertNull(linkedBlockingQueue.poll());

        linkedBlockingQueue.add("a");
        Assert.assertEquals("a", linkedBlockingQueue.remove());
        Assert.assertThrows(NoSuchElementException.class, () -> linkedBlockingQueue.remove());
    }

    @Test
    public void testGetElementAndBlockWhenEmpty() {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(2);
        Thread thread = new Thread(() -> {
            try {
                String a = linkedBlockingQueue.take();
                System.out.println(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    @Test
    public void testPutAndTakeElement() {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(1);
        Thread productor = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                String uuid = UUID.randomUUID().toString();
                System.out.println(LocalDateTime.now() + " Star to put element: " + uuid);
                try {
                    linkedBlockingQueue.put(uuid);
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
