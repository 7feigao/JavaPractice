import org.junit.Test;

public class TestDefeatCheckedException {
@Test
    public void testException(){
    A a=new A();
    a.test();
}
}

class A{
    @SuppressWarnings("unchcked")
    public static <T extends Throwable> void throwAs(Throwable t) throws T {
        throw (T)t;
    }
    public void test(){
        try {
            int i = 1 / 0;
        }catch (Exception e){
            A.<ArithmeticException>throwAs(e);
        }
    }
}
