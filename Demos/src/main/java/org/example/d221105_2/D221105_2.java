package org.example.d221105_2;

public class D221105_2 {
    public static void main(String[] args) {
        C1 c1 = new C1();
        c1.print();
        c1.printDefault();
        c1.printDefault2();
    }
}

class C1 implements I1, I2 {
    @Override
    public void print() {
        System.out.println("print from class");
    }

    @Override
    public void printDefault2() {
        I1.super.printDefault2();
    }
}

interface I1 {
    public void print();

    public default void printDefault() {
        System.out.println("default method from interface");
    }

    public default void printDefault2() {
        System.out.println("default 2 method from interface");
    }
}

interface I2 {
    public void printDefault2();
}