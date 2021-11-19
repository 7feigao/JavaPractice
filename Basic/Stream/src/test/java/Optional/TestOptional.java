package Optional;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TestOptional {
    final Optional<String> stringOptional = Optional.of("hello");
    final Optional<String> nullOptional = Optional.empty();
    @Test
    public void testGetValue() {

        System.out.println(stringOptional.get());
        Assert.assertThrows(NoSuchElementException.class, () -> System.out.println(nullOptional.get()));
        System.out.println(stringOptional.orElse("world"));
        System.out.println(nullOptional.orElse("world"));
        System.out.println(stringOptional.orElseGet(() -> "world"));
        System.out.println(nullOptional.orElseGet(() -> "world"));
        System.out.println(stringOptional.orElseThrow(() -> new NullPointerException("should with value")));
        Assert.assertThrows(NullPointerException.class, () -> nullOptional.orElseThrow(() -> new NullPointerException("should with value")));
    }

    @Test
    public void testConsumeValue(){
        stringOptional.ifPresent(System.out::println);
        nullOptional.ifPresent(System.out::println);//nothing to output
    }
    @Test
    public void testPipelineValue(){
        System.out.println(stringOptional.map(String::toLowerCase).get());
        List<String> list=new LinkedList<>();
        Optional<Boolean> isAdded=stringOptional.map(list::add);

        stringOptional.filter(rec->rec.length()>4).map(String::length).ifPresent(System.out::println);
    }

    @Test
    public void testOptionalFlatMap(){
        /** flatmap is used to get element of type U from class T and then wrap it as Option<U>*/
        stringOptional.flatMap(rec->Optional.ofNullable(rec.length()));
    }

}
