package org.example.d221107_1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class D221107_1_Demon_Thread {
    public static void main(String[] args) {
        Thread demonThread=new Thread(()->{
            while (true){
                System.out.println(new Date().toString());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        demonThread.setDaemon(true);
        demonThread.start();

        Thread thread=new Thread(()->{
            int i=0;
            while(i++<6){
                System.out.println("exec: "+new Date());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        try {
            thread.join();
//            demonThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
