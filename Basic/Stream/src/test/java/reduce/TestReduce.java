package reduce;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

public class TestReduce {
@Test
    public void testReduce(){
    Optional<Integer> sum=Stream.of(1,2,3,4,5,6).reduce((r1, r2)->r1+r2);
    Optional<Integer> sum1=Stream.of(1,2,3,4,5,6).reduce(Integer::sum);
    System.out.println(sum.orElse(Integer.MIN_VALUE));
    System.out.println(sum1.orElse(Integer.MIN_VALUE));
    Integer sum2=Stream.of(1,2,3,4,5,6).reduce(0,Integer::sum);
    System.out.println(sum2);

    Integer totalLength=Stream.of("hello","world","test").reduce(0,(partSum,str)->partSum+str.length(),Integer::sum);
    System.out.println(totalLength);
}
}
