import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestConvertListToOtherObject {
    @Test
    public void testList2Array() {
        List<String> listStr = Stream.of("hello", "world", "!").collect(Collectors.toList());
        String[] strArr = listStr.toArray(new String[0]);
        Assert.assertTrue(strArr.length == 3);
        Assert.assertEquals("hello", strArr[0]);
        Assert.assertEquals("!", strArr[2]);

    }
}
