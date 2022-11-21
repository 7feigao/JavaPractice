package org.example;

import org.junit.Test;

public class TestDecimalCompare {
    @Test
    public void testCompareFloat(){
        int res=DecimalCompare.compareFloat(0.00204992737f,0.00204996183f);
        assert res==0;
        int res1=DecimalCompare.compareFloat(1234567890f,1234567888f);
        assert res1==0;
        int res2=DecimalCompare.compareFloat(1.000000001f,0.99999999998f);
        assert res2==0;
        int res3=DecimalCompare.compareFloat(1234567890f,1234577890f);
        assert res3<0;
        int res4=DecimalCompare.compareFloatByPrecision(1234567890f,1234577890f,5);
        assert res4==0;
        System.out.println();
    }
    @Test
    public void testCompare(){
        DecimalCompare decimalCompare=new DecimalCompare("round:6");
        int res=decimalCompare.compare(1.23456789,1.23456890);
        assert 0==res;
        int res1=decimalCompare.compare(1.23457789,1.23456890);
        assert res1>0;
        System.out.println();
    }
}
