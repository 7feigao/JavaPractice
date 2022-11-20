package org.example.d221116;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestArrayParallelSort {
    public static void main(String[] args) {
        testSort();
        testParallelSet();
    }
    public static void testSort(){
        int[] a=new int[]{7,21,343,12,32,2321,342,432,12,34};
        Arrays.parallelSort(a);
        System.out.println(Arrays.stream(a).mapToObj(i->i+"").collect(Collectors.joining(",")));
    }
    public static void testParallelSet(){
        int[] a=new int[20];
        Arrays.parallelSetAll(a,i->i%5);
        System.out.println(Arrays.stream(a).mapToObj(i->i+"").collect(Collectors.joining(",")));

    }
}
