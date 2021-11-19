package collect;

import org.junit.Test;

import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCollect {

    @Test
    public void testToArray(){
        Object[] objArr=Stream.generate(Math::random).limit(10).toArray();
        Double[] doubleArr=Stream.generate(Math::random).limit(10).toArray(Double[]::new);
        System.out.println();
    }

    @Test
    public void testToCollection(){
        TreeSet<Double> treeSet=Stream.generate(Math::random).collect(Collectors.toCollection(TreeSet::new));

    }
}
