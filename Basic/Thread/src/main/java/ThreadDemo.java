import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ThreadDemo {
    public static void main(String[] args) {
        TestC testC=new TestC();
        testC.print();
        test(()->{
            System.out.println("hello from ano class");
        });
        Thread thread=new Thread(()->{
            while (true) {
                System.out.println(new Date().toString());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setName("print_current_date");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
public static void test(TestFunctionalInterface c){
        c.print();
        c.printDefault();
}
}

@FunctionalInterface
interface TestFunctionalInterface{
    public void print();
    default void printDefault(){
        System.out.println("hello from default method");
    }
}



interface TestInt2{
    public void print();
    void printDefault();
}

class TestC implements TestFunctionalInterface,TestInt2{

    @Override
    public void print() {
        System.out.println("from default");
    }

    @Override
    public void printDefault() {
        TestFunctionalInterface.super.printDefault();
    }
}
//
//class TestF implements TestFunctionalInterface{
//
//    @Override
//    public void print() {
//
//    }
//}