import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestMap {
    @Test
    public void testMap(){
        Map<String,String> testMap=new HashMap<>();
        testMap.put("hello","world");
        testMap.put("123","456");

        testMap.merge("123", "789", (rec1,rec2) -> rec1+rec2);
        testMap.merge("789","012",(rec1,rec2)->rec1+rec2);
        for(Map.Entry<String,String> eachEntry:testMap.entrySet()){
            System.out.println(String.format("%s:%s",eachEntry.getKey(),eachEntry.getValue()));
        }
        /**output:
         * 123:456789
         * 789:012
         * hello:world*/
    }
    @Test
    public void testLinkedHashMap(){
        Map<String,String> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put("123","456");
        linkedHashMap.put("223","780");
        linkedHashMap.keySet().forEach(System.out::println);
        linkedHashMap.get("123");
        linkedHashMap.keySet().forEach(System.out::println);

    }
}
