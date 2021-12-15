import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

public class TestGeneric {
    class TestGnericInJava<T extends Object, E extends Collection> {
        public boolean test(T a, T b) {
            return a.equals(b);
        }

        public boolean test(E a, E b) {
            return a.equals(b);
        }
    }

    @Test
    public void testRun() {

        TestGnericInJava<List, LinkedList> a = new TestGnericInJava<>();
        System.out.println();
        a.test(new LinkedList(), new LinkedList());
        List<String> list=new LinkedList<>();
    }
}
