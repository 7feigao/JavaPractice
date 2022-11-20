package org.example.d221119;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestRecursiveTask {
    public static void main(String[] args) {
        int capacity=100000000;
        int[] a=new int[capacity];
        long sum=0;
        for (int i = 0; i < capacity; i++) {
            a[i]=(int)(Math.random()*10);
            sum+=a[i];
        }
       ForkJoinPool forkJoinPool= new ForkJoinPool();
        RecursiveSum recursiveSum=new RecursiveSum(a,0,capacity);
        forkJoinPool.invoke(recursiveSum);
        assert recursiveSum.join()==sum;
        System.out.println(sum);
    }
}

class RecursiveSum extends RecursiveTask<Long>{
    private int[] arr;
    private int start;
    private int end;

    public RecursiveSum(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if(end-start<=1){
            return Long.valueOf(arr[start]);
        }else{
            int mid=(start+end)/2;
            RecursiveSum left=new RecursiveSum(arr,start,mid);
            RecursiveSum right=new RecursiveSum(arr,mid,end);
            invokeAll(left,right);
            return left.join()+right.join();
        }
    }
}