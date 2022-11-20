package org.example.d221105_1;

public class D221105_1 {
    public static void main(String[] args) {
        C1 c1=new C1();
        printC1(c1);
        printI1(c1);
        printI2(c1);
    }
public static void printC1(C1 c1){
    c1.print();
}
public static void printI1(I1 i1){
    i1.print();
}

public static void printI2(I2 i2){
    i2.print();
}
}

class C1 implements I1,I2{

    @Override
    public void print() {
        System.out.println("hello");
    }
}
interface I1{
public void print();
}
interface I2{
    public void print();
}

