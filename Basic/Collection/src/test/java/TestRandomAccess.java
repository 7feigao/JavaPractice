import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;

public class TestRandomAccess {
    @Test
    public void testRandomAccess() {
        List<String> list=new LinkedList<>();
        List<String> list1=new ArrayList<>();
        Assert.assertTrue(list1 instanceof RandomAccess);
        Assert.assertFalse(list instanceof RandomAccess);
    }
}
