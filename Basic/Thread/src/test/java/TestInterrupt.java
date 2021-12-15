import org.junit.Test;

public class TestInterrupt {
    @Test
    public void testInterrupt() {
        Runnable runnable = () -> {
            try {

                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("active");
                    for (long i = 0; i <1000000000L ; i++) {
                    }
//                    Thread.sleep(100);
                }
            } catch (Exception e) {
                //catch error when interrupt during sleep or block
                System.out.println("Interrupt during sleep or block");
            } finally {
                //cleanup
            }

        };
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        System.out.println("Interrupted");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish");

        /**
         * LOG:
         * active
         * active
         * active
         * active
         * Interrupted
         * Finish
         */
    }

    @Test
    public void testInterruptWithSleep() {
        Runnable runnable = () -> {
            try {
                while (true) {
                    System.out.println("active");
                    Thread.sleep(100);//sleep will cleanup the interrupt statue and throw InterruptedException
                }
            } catch (Exception e) {
                //catch error when interrupt during sleep or block
                System.out.println("Interrupt during sleep or block");
            } finally {
                //cleanup
            }

        };
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        System.out.println("Interrupted");
        try {
            thread.join();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish");

        /**
         active
         active
         active
         active
         active
         active
         active
         active
         active
         active
         Interrupted
         Interrupt during sleep or block
         Finish
         */
    }

    @Test
    public void testInterruptedVsIsInterrupted(){
        Runnable runnableInterrupted=()->{
            while (true){
                if(Thread.interrupted()){//will clean the interrupt status
                    System.out.println("Interrupted0:"+Thread.interrupted());
                    System.out.println("IsInterrupted0:"+Thread.currentThread().isInterrupted());
                    return;
                }
            }
        };
        Runnable runnableIsInterrupted=()->{
          while (true){
              if(Thread.currentThread().isInterrupted()){//won't cleanup current interrupt status
                  System.out.println("IsInterrupted1:"+Thread.currentThread().isInterrupted());
                  System.out.println("Interrupted1:"+Thread.interrupted());
                  System.out.println("IsInterrupted2:"+Thread.currentThread().isInterrupted());
                  return;
              }
          }
        };
        Thread thread=new Thread(runnableInterrupted);
        Thread thread1=new Thread(runnableIsInterrupted);
        thread.start();
        thread1.start();
        thread.interrupt();
        thread1.interrupt();
        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * log://the order of output might be different
         * Interrupted0:false
         * IsInterrupted0:false
         * IsInterrupted1:true
         * Interrupted1:true
         * IsInterrupted2:false*/


    }

}
