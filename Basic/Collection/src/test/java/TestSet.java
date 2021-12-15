import org.junit.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestSet {
    class A {
        public String a;
        public int b;

        public A(String a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "A{" +
                    "a='" + a + '\'' +
                    ", b=" + b +
                    '}';
        }
    }

    @Test
    public void testTreeSet() {
        TreeSet<A> treeSet = new TreeSet<>(new Comparator<A>() {
            @Override
            public int compare(A o1, A o2) {
                if (o1.a.equals(o2.a)) {
                    return o1.b - o2.b;
                } else {
                    return o1.a.compareTo(o2.a);
                }
            }
        });

        Collection<A> collection = Stream.of(new A("aaa", 9), new A("ccc", 2), new A("bbb", 5)).collect(Collectors.toCollection(LinkedList::new));
        treeSet.addAll(collection);
        treeSet.stream().forEach(System.out::println);
        TreeSet<A> treeSet1 = new TreeSet<>(new Comparator<A>() {
            @Override
            public int compare(A o1, A o2) {
                if (o1.b == o2.b) {
                    return o1.a.compareTo(o2.a);
                } else {
                    return o1.b - o2.b;
                }
            }
        });
        treeSet1.addAll(collection);
        treeSet1.stream().forEach(System.out::println);

        /**
         * A{a='aaa', b=9}
         * A{a='bbb', b=5}
         * A{a='ccc', b=2}
         * A{a='ccc', b=2}
         * A{a='bbb', b=5}
         * A{a='aaa', b=9}*/
    }
}
