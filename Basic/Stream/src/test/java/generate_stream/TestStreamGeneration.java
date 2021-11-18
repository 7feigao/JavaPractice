package generate_stream;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestStreamGeneration {
    @Test
    public void array2stream(){
        int[] arr=new int[]{0,1,2,3,4,5,6,7,8,9,10};
        Arrays.stream(arr).forEach(System.out::println);// output 0-10
        Arrays.stream(arr,2,5).forEach(System.out::println);// output 2 3 4
    }

    @Test
    public void pattern2stream(){
        Pattern.compile("\\PL+").splitAsStream("hello world\ntest1 test2").forEach(System.out::println);
        /*
        * output:
        *   hello
            world
            test
            test*/
    }

    @Test
    public void steam_of(){
        Stream.of(1,2,3,4).forEach(System.out::println);
    }

    @Test
    public void stream_generate(){
        /*
        * @tag infinite stream*/
        Stream.generate(()->"test").limit(10).forEach(System.out::println); // output "test" for ten times
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }
    @Test
    public void stream_iterate(){
        Stream.iterate(1,rec->rec+1).limit(10).forEach(System.out::println);
//        Stream.iterate(1,rec->rec<=10,rec->rec+1)
        Stream.iterate(BigInteger.ZERO,rec->rec.add(BigInteger.valueOf(3))).limit(10).forEach(System.out::println);
    }

    @Test
    public void streamFromPattern(){
        Pattern.compile("\\P{L}+").splitAsStream("hello world tes12tes, hello2,\n 你好，再见。").forEach(System.out::println);
        /**
         * output:
         hello
         world
         tes
         tes
         hello
         你好
         再见
          */
    }

    @Test
    public void streamFromIterable(){
        Iterable<Integer> integers=Stream.of(1,2,3,4,5,6,7,8,9).collect(Collectors.toList());
        StreamSupport.stream(integers.spliterator(), false).forEach(System.out::println);
    }



}
