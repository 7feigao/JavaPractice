package generate_stream;

import org.junit.Test;

import java.util.Comparator;
import java.util.stream.Stream;

public class TestStreamTransformation {
    @Test
    public void testLimit() {
        Stream.of(1, 2, 3, 4, 5).limit(3).forEach(System.out::println);//output: 1,2,3
        Stream.of(1, 2, 3, 4, 5).limit(8).forEach(System.out::println);//output: 1,2,3,4,5
    }

    @Test
    public void testSkip() {
        Stream.of(1, 2, 3, 4, 5).skip(3).forEach(System.out::println);//output: 4,5
        Stream.of(1, 2, 3, 4, 5).limit(8).forEach(System.out::println);//output: nothing
    }

    @Test
    public void testSort() {
        Stream.of(1, 5, 3, 12, 564, 321, 43, 2154, 32, 431, 2).sorted(Comparator.comparingInt(rec -> rec)).forEach(System.out::println);
        Stream.of(1, 5, 3, 12, 564, 321, 43, 2154, 32, 431, 2).sorted(Comparator.comparingInt(rec -> (int) rec).reversed()).forEach(System.out::println);

    }

    @Test
    public void testPeek(){
        Stream.of(1,2,3,4,5,2,1).peek(System.out::println).mapToInt(rec->rec).sum();
    }
}
