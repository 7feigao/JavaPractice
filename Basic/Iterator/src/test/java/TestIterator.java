import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestIterator {
    @Test
    public void testSimleIterator(){
        SimpleIterable simpleIterable=new SimpleIterable();
        for(int i : simpleIterable){
            System.out.println(i);
        }

    }
}

class SimpleIterator implements Iterator<Integer> {
    private int i = 0;
    private int limit;

    public SimpleIterator(int limit) {
        this.limit = limit;
    }

    public SimpleIterator() {
        this(10);
    }

    @Override
    public boolean hasNext() {
        return i < limit;
    }

    @Override
    public Integer next() {
        if(hasNext()){
            int tempi=i;
            i++;
            return tempi;
        }else{
            throw new NoSuchElementException();
        }
    }
}


class SimpleIterable implements Iterable<Integer>{


    @Override
    public Iterator<Integer> iterator() {
        return new SimpleIterator();
    }
}