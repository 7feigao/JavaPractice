package org.example.d221111_1;

import java.util.concurrent.atomic.*;

public class D221111_1_Atomics {
    public static void main(String[] args) {
        AtomicLong atomicLong=new AtomicLong();
        atomicLong.addAndGet(10);

        //atomic update field inside class
        TestAtomicUpdate testAtomicUpdate=new TestAtomicUpdate("test");
        AtomicLongFieldUpdater<TestAtomicUpdate> atomicUpdateAtomicLongFieldUpdater=AtomicLongFieldUpdater.newUpdater(TestAtomicUpdate.class,"id");
        AtomicIntegerFieldUpdater<TestAtomicUpdate> atomicUpdateAtomicLongFieldUpdaterSize=AtomicIntegerFieldUpdater.newUpdater(TestAtomicUpdate.class,"size");
        AtomicReferenceFieldUpdater<TestAtomicUpdate,String> atomicUpdateStringAtomicReferenceFieldUpdater=AtomicReferenceFieldUpdater.newUpdater(TestAtomicUpdate.class,String.class,"name");
        atomicUpdateAtomicLongFieldUpdater.addAndGet(testAtomicUpdate,10);
        atomicUpdateAtomicLongFieldUpdaterSize.getAndAdd(testAtomicUpdate,10);
        atomicUpdateStringAtomicReferenceFieldUpdater.set(testAtomicUpdate,"hello");
        System.out.println(testAtomicUpdate.getId());
        System.out.println(testAtomicUpdate.getSize());
        System.out.println(testAtomicUpdate.getName());

    }
}



class TestAtomicUpdate{
    protected volatile long id=0;
    protected volatile int size=0;
    protected volatile String name;

    public TestAtomicUpdate(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
