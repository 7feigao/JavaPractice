import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

public class TestVarargsWarnings {
    @SafeVarargs
    public static <T> void addAll(Collection<T> collection, T... elements) {
        for (T t : elements) {
            collection.add(t);
        }
    }

    @Test
    public void testVarargsWarnings() {
        ArrayList<Map<String, Integer>> arrayList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("b", 2);
        addAll(arrayList, map, map2);
    }

}
