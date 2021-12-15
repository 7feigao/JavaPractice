public class TestWildcards {
    class A {
        public String getA() {
            return "A";
        }
    }

    class B extends A {
        public String getB() {
            return "B";
        }
    }

    class C<T> {
        private T t;

        public T getT() {
            return t;
        }

        public C<T> setTe(T t) {
            this.t = t;
            return this;
        }
    }

    class D {
        public void getE(C<? extends A> a) {
            a.getT().getA();
        }

        public void getS(C<?  super B > a) {
            a.getT().hashCode();
        }
    }
}

