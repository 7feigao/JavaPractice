package generate_stream;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStreamReductions {
    @Test
    public void testSimpleReductions(){
        List<Integer> integers= Stream.of(1,4,3,2,65,65,2,34,54,123).collect(Collectors.toList());
        Assert.assertTrue(integers.stream().allMatch(integer -> integer>0));
    }
}
