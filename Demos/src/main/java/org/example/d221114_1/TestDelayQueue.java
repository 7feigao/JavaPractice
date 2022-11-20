package org.example.d221114_1;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class TestDelay {
    public static void main(String[] args) {
        delay();
    }

    public static void delay() {
        DelayQueue<DelayedTime> delayedTimes = new DelayQueue<>();
        for (int i = 0; i < 10; i++) {
            delayedTimes.put(new DelayedTime(new Date(), 5 * 1000+1000*(int)(Math.random()*10-5)));
        }
        while (delayedTimes.size() != 0) {
            try {
                DelayedTime delayedTime = delayedTimes.take();
                System.out.println("Delay Time:" + (new Date().getTime() - delayedTime.getDate().getTime())+" expect delay: "+delayedTime.getDelay());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}


class DelayedTime implements Delayed {
    private Date date;
    private long delay;
    private Date startDate = new Date();

    public DelayedTime(Date date, long delay) {
        this.date = date;
        this.delay = delay;
    }

    public Date getDate() {
        return date;
    }

    public long getDelay() {
        return delay;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - (new Date().getTime() - startDate.getTime()), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}