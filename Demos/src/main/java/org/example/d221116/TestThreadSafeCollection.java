package org.example.d221116;

import java.util.*;

public class TestThreadSafeCollection {
    public static void test(){
        List<String> list= Collections.synchronizedList(new LinkedList<>());
        Set<String> set=Collections.synchronizedSet(new HashSet<>());
        Map<String,String> map=Collections.synchronizedMap(new HashMap<>());
    }
}
