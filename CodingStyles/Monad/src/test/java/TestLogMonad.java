import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class TestLogMonad {
    @Test
    public void testLogMonad(){
        LogMonad<Integer> integerLogMonad=LogMonad.of(10);
        Function<Integer,LogMonad<Double>> multiDoubleMonad=value->LogMonad.of((value*value)*3.14);
        Function<Double,LogMonad<List<BigDecimal>>> double2BigDecListMonad=value->{
            List<BigDecimal> bigDecimals=new LinkedList<>();
            bigDecimals.add(BigDecimal.valueOf(value));
            return LogMonad.of(bigDecimals);
        };
        integerLogMonad.log().flatMap(multiDoubleMonad).log().flatMap(double2BigDecListMonad).log();
    }
}
