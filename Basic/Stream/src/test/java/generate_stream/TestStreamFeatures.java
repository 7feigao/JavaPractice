package generate_stream;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class TestStreamFeatures {
    @Test
    public void testStreamDonnotMutateSource(){
        Stream<Integer> integerStrea=Stream.of(1,2,3,4,5,6,7,8);
        System.out.println(integerStrea.mapToInt(rec->rec).sum());

    }
}
