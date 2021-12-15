import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCollectors {
    @Test
    public void testUnmodifiedViews() {
        List<String> list= Stream.of("a","B","c").collect(Collectors.toList());
        List<String> unmodifiedList= Collections.unmodifiableList(list);
        list.add("d");
        unmodifiedList.stream().forEach(System.out::println);
        Assert.assertThrows(UnsupportedOperationException.class,()->unmodifiedList.add("e"));
    }
}
