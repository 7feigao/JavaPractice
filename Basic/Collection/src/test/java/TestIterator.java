import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestIterator {
    @Test
    public void testIterator() {
        List<String> s = Stream.of("aaa", "bbb", "ccc", "ddd").collect(Collectors.toList());
        Iterator<String> si = s.iterator();
        while (si.hasNext()) {
            String s1 = si.next();
            if (s1.equals("aaa") || s1.equals("bbb")) {
                si.remove();
                Assert.assertThrows(IllegalStateException.class, () -> si.remove());
            }
        }
        Assert.assertEquals(2, s.size());
        Assert.assertTrue(s.contains("ccc"));
        Assert.assertFalse(s.contains("aaa"));
        Assert.assertThrows(NoSuchElementException.class, () -> si.next());
    }

    @Test
    public void testListIterator() {
        List<String> s = Stream.of("aaa", "bbb", "ccc", "ddd").collect(Collectors.toList());
        ListIterator<String> listIterator = s.listIterator();
        while (listIterator.hasNext()) {
            String s1 = listIterator.next();
            if (s1.equals("bbb")||s1.equals("ddd")){
                listIterator.add("aabb");
            }
        }
        Assert.assertTrue(s.contains("aabb"));
        s.stream().forEach(System.out::println);
        /**
         *aaa
         * bbb
         * aabb
         * ccc
         * ddd
         * aabb */
        ListIterator<String> listIterator1=s.listIterator(s.size());
        while ((listIterator1.hasPrevious())){
            System.out.println(listIterator1.previous());
        }
        /**
         * aabb
         * ddd
         * ccc
         * aabb
         * bbb
         * aaa*/
    }

}
