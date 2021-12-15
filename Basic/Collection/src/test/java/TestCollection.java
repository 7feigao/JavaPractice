import org.junit.Assert;
import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCollection {
    @Test
    public void testCollection() {
        Collection<String> collection = Stream.of("a", "bb", "ccc", "dddd", "eeeee").collect(Collectors.toCollection(LinkedList::new));
        Assert.assertEquals(5, collection.size());
        Collection<String> collection1 = Stream.of("ffffff", "ggggggg").collect(Collectors.toList());
        Assert.assertFalse(collection.containsAll(collection1));
        Assert.assertTrue(collection.addAll(collection1));
        Assert.assertFalse(collection.addAll(new LinkedList<>()));
        Assert.assertEquals(7, collection.size());
        Assert.assertTrue(collection.containsAll(collection1));
        Assert.assertTrue(collection.removeIf(rec->rec.length()<3));
        Assert.assertFalse(collection.contains("a"));
        Assert.assertFalse(collection.contains("bb"));
        Assert.assertEquals(5,collection.size());
        Assert.assertTrue(collection.retainAll(collection1));
        Assert.assertTrue(collection.equals(collection1));
    }
}
