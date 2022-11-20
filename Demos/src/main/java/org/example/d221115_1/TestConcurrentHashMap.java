package org.example.d221115_1;

import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        ConcurrentHashMap<String,Integer> concurrentHashMap=new ConcurrentHashMap<>();
        concurrentHashMap.putIfAbsent("test",8);//put 8
        concurrentHashMap.merge("test",4,Integer::sum);// add 4
        concurrentHashMap.putIfAbsent("test",8);//won't put
        System.out.println(concurrentHashMap.get("test"));
        concurrentHashMap.computeIfPresent("test",(key,val)->key.length()+val);
        System.out.println(concurrentHashMap.get("test"));
        String res=concurrentHashMap.search(10,(k,v)->{
            if(k.equals("test")&&v==16){
                return k+":"+v;
            }
            return null;
        });
        System.out.println(res);
    }
}
