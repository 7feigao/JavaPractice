import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TestConCurrentHashMap {
    class Str {
        private String s;

        public Str(String s) {
            this.s = s;
            System.out.println("Constructed: " + s);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Str)) return false;
            Str str = (Str) o;
            return Objects.equals(s, str.s);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s);
        }
    }

    @Test
    public void testConcurrentHashmap() {
        ConcurrentHashMap<String, Str> concurrentHashMap = new ConcurrentHashMap<>();
        {
            concurrentHashMap.putIfAbsent("hello", new Str("hello"));//output: Constructed: hello
            concurrentHashMap.putIfAbsent("hello", new Str("hello"));//output: Constructed: hello
        }
        {
            Assert.assertThrows(NullPointerException.class, () -> concurrentHashMap.putIfAbsent("hello1", null));
        }
        {
            concurrentHashMap.compute("world", (k, v) -> Objects.isNull(v) ? new Str(k) : new Str(v.s + k));//output: Constructed: world
            System.out.println(concurrentHashMap.get("world").s);//output: world
            concurrentHashMap.compute("world", (k, v) -> Objects.isNull(v) ? new Str(k) : new Str(v.s + k));//output: Constructed: world
            System.out.println(concurrentHashMap.get("world").s);//output: Constructed: worldworld
        }
        {
            Assert.assertTrue(concurrentHashMap.containsKey("world"));
            concurrentHashMap.compute("world", (k, v) -> null);
            Assert.assertFalse(concurrentHashMap.containsKey("world"));
        }
        {
            concurrentHashMap.merge("merge", new Str("merge1"), (existVal, newVal) -> Objects.isNull(existVal) ? newVal : new Str(existVal.s + newVal.s));//output: Constructed: merge1
            concurrentHashMap.merge("merge", new Str("merge2"), (existVal, newVal) -> Objects.isNull(existVal) ? newVal : new Str(existVal.s + newVal.s));//output: Constructed: merge1merge2
        }
        {
            Assert.assertTrue(concurrentHashMap.containsKey("merge"));
            concurrentHashMap.merge("merge", new Str("merge3"), (v1, v2) -> null);
            Assert.assertFalse(concurrentHashMap.containsKey("merge"));
        }

        HashMap<String, Str> hashMap = new HashMap<>();
        hashMap.putIfAbsent("hello", new Str("hello"));//output: Constructed: hello
        hashMap.putIfAbsent("hello", new Str("hello"));//output: Constructed: hello
        hashMap.computeIfAbsent("h1", k -> new Str(k));//output:  Constructed: h1
        hashMap.computeIfAbsent("h1", k -> new Str(k));//no output
        hashMap.putIfAbsent("hello1", null);
        hashMap.compute("world", (k, v) -> Objects.isNull(v) ? new Str(k) : new Str(v.s + k));//output: Constructed: world
        System.out.println(hashMap.get("world").s);//output: world
        hashMap.compute("world", (k, v) -> Objects.isNull(v) ? new Str(k) : new Str(v.s + k));//output: Constructed: worldworld
        System.out.println(hashMap.get("world").s);//output: worldworld
        /**
         * output:
         * Constructed
         * Constructed
         * Constructed
         * Constructed*/
        System.out.println();
    }
}
