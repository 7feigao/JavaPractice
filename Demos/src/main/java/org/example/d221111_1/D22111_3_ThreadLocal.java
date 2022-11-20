package org.example.d221111_1;

import java.util.concurrent.ThreadLocalRandom;

public class D22111_3_ThreadLocal {
    public static void main(String[] args) {
        TestThreadLocal testThreadLocal=new TestThreadLocal("test",100);
        testThreadLocal.start();
        try {
            testThreadLocal.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class TestThreadLocal extends Thread{
    ThreadLocal<TestLocalVar> testLocalVarThreadLocal;
    public TestThreadLocal(String name,int size) {
        testLocalVarThreadLocal=ThreadLocal.withInitial(()->new TestLocalVar(name,size));
    }
    public void run(){
        ThreadLocalRandom.current().nextInt();
        testLocalVarThreadLocal.get().setSize(100);
        testLocalVarThreadLocal.remove();
        testLocalVarThreadLocal.get().setSize(1000);
    }

}

class TestLocalVar{
    private String name;
    private int size;

    public TestLocalVar(String name, int size) {
        this.name = name;
        this.size = size;
        System.out.println("Thread "+Thread.currentThread().getName()+" init name "+name+" size "+size);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}