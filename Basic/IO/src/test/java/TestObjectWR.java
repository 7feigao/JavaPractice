import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Date;

public class TestObjectWR implements Serializable{
    class A implements Serializable{
        public int a=12;
        public String b="hello";
    }
    class B implements Serializable{
        public A a=new A();
        public String v="asdf";
        public transient String t="hello";
    }
    @Test
    public void testObjectReadWrite(){
        File objectFile=new File("object.bat");
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(objectFile));
            B b1=new B();
            objectOutputStream.writeObject(b1);
            B b2=new B();
            b2.a=b1.a;
            b2.t="fdsagfgd";
            objectOutputStream.writeObject(b2);
            objectOutputStream.close();
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(objectFile));
            B b3= (B) objectInputStream.readObject();
            B b4= (B) objectInputStream.readObject();
            Assert.assertNull(b3.t);
            Assert.assertNull(b4.t);
            Assert.assertEquals(System.identityHashCode(b3.a),System.identityHashCode(b4.a));
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    class C implements Serializable{
        public int a;
        public double b;

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(a);
            objectOutputStream.writeDouble(b>0?b:-1.0);
        }
        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.a=objectInputStream.readInt();
            this.b=objectInputStream.readDouble();
        }
    }
    @Test
    public void testCustomWR(){
        File output=new File("custom.bat");
        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(output));
            C c=new C();
            c.a=199;
            c.b=19;
            objectOutputStream.writeObject(c);
            c=new C();
            c.a=1000;
            c.b=-231;
            objectOutputStream.writeObject(c);
            objectOutputStream.close();
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(output));
            C ci1= (C) objectInputStream.readObject();
            C ci2= (C) objectInputStream.readObject();
            Assert.assertEquals(0,Double.compare(-1.0,ci2.b));
            System.out.println();
            new Date();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
