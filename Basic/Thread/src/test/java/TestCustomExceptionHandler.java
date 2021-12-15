import org.junit.Test;

public class TestCustomExceptionHandler {
    class MyExceptionHandler implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println(String.format("thread name: %s",t.getName()));
            System.err.println("Catched by custom handler: "+e.getMessage());
        }
    }

    @Test
    public void testCustomExceptionHandler(){
        Runnable runnable=()->{
            double a=1/0;
        };
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*
         * log of above code:
         * Exception in thread "Thread-0" java.lang.ArithmeticException: / by zero
         * 	at TestCustomExceptionHandler.lambda$testCustomExceptionHandler$0(TestCustomExceptionHandler.java:16)
         * 	at java.lang.Thread.run(Thread.java:748)*/
        thread=new Thread(runnable);
        thread.setUncaughtExceptionHandler(new MyExceptionHandler());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
        /**
         * log:
         * thread name: Thread-1
         * Catched by custom handler: / by zero
         * Done!
         * */
    }
}
