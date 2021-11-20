package generate_stream;

import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestPrimitiveStream {

    @Test
    public void testIntStream(){
        int sum=IntStream.of(1,2,3,4,5,6).sum();
        IntStream zeroTo99=IntStream.range(0,100);
        System.out.println(zeroTo99.count());
        IntStream zeroTo100=IntStream.rangeClosed(0,100);
        System.out.println(zeroTo100.count()


        );
    }
    @Test
    public void testInt2Integer(){
        List<Integer> integerList=IntStream.of(1,2,3,4,5,6).boxed().collect(Collectors.toList());
    }

    @Test
    public void testInteger2Int(){
        System.out.println(Stream.of(1,2,3,4,5,6).mapToInt(Integer::intValue).sum());
    }
}
