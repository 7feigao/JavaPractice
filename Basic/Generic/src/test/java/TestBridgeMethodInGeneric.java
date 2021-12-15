import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TestBridgeMethodInGeneric {
    class A<E> {
        private E first;
        private E second;

        public E getFirst() {
            return first;
        }

        public void setFirst(E first) {
            this.first = first;
        }

        public E getSecond() {
            return second;
        }

        public A<E> setSecond(E second) {
            this.second = second;
            return this;
        }
    }

    class B extends A<String> {
        public void setFirst(String s) {
            if (s.length() > 10) {
                super.setFirst(s.substring(0, 10));
            } else
                super.setFirst(s);
        }

        public void setFirst(LocalDate s) {
            super.setFirst(s.toString());
        }
//        you can not define this function
//        public void setFirst(Object o){
//            super.setFirst((String) o);
//        }
    }

    class C {
        private String s;
        public void setFirst(String s) {
            this.s = s;
        }

        public void setFirst(Object s) {
            this.s=s.toString().substring(0,7);
        }

    }

    @Test
    @SuppressWarnings("checked")
    public void testBridgeMethodInGeneric() {
        A<String> a = new B();
        a.setFirst("abcdefghijkl");
        Assert.assertEquals("abcdefghij", a.getFirst());
        System.out.println(a.getFirst());
    }

}
