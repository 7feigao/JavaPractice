import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Objects;

public class TestClassObjects {
    @Test
    public void testIfObjectIsNull() {
        Assert.assertFalse(Objects.isNull(BigDecimal.ONE));
        Assert.assertFalse(Objects.nonNull(null));
        Assert.assertTrue(Objects.isNull(null));
        Assert.assertTrue(Objects.nonNull(BigDecimal.ONE));
        Assert.assertThrows(NullPointerException.class, () -> Objects.requireNonNull(null));
        Assert.assertThrows("should not be null", NullPointerException.class, ()->Objects.requireNonNull(null, "should not be null"));
    }
}
