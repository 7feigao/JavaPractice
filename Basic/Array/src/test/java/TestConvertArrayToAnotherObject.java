import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestConvertArrayToAnotherObject {
    @Test
    public void arrayToList(){
        String[] array=new String[]{"hello","world"};
        List<String> strList=Arrays.asList(array);
        Assert.assertTrue(2==strList.size());
    }
}
