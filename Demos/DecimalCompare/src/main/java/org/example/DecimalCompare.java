package org.example;

import org.decimal4j.util.DoubleRounder;

import java.math.BigDecimal;

public class DecimalCompare {
    public static enum MODE {
        PLAIN, // use Float.compare
        ROUND, // only compare first <threshold> digit, in half-up way
        PERCENT, // abs(n1-n2)/min(n1,n2)<threshold
        SUBSTRACT // abs(n1-n2)<threshold
    }

    ;
    public static final BigDecimal DEFAULT_FLOAT_ROUND_THRESHOLD = BigDecimal.valueOf(6);
    public static final BigDecimal DEFAULT_DOUBLE_ROUND_THRESHOLD = BigDecimal.valueOf(10);
    public static final BigDecimal DEFAULT_FLOST_PERCENT_THRESHOLD = BigDecimal.valueOf(10E-5);
    public static final BigDecimal DEFAULT_DOUBLE_PERCENT_THRESHOLD = BigDecimal.valueOf(10E-9);
    public static final BigDecimal DEFAULT_FLOST_SUBSTRACT_THRESHOLD = BigDecimal.valueOf(10E-5);
    public static final BigDecimal DEFAULT_DOUBLE_SUBSTRACT_THRESHOLD = BigDecimal.valueOf(10E-9);
    private MODE compareMode;
    BigDecimal threshold;

    public DecimalCompare(MODE compareMode, BigDecimal threshold) {
        this.compareMode = compareMode;
        this.threshold = threshold;
    }

    public DecimalCompare(String modeThreshold){
        /**
         * for mode with threshold: <mode_str>:<threshold>
         * for mode without threshold: <mode_str>
         *     eg:
         *          round:6
         *          percent:0.000001
         *          plain
         *          substract:0.000001
         *          */
        String[] modeThresholdArr=modeThreshold.split(":");
        String modeStr=modeThresholdArr[0];
        MODE mode;
        switch (modeStr){
            case "plain":
                mode=MODE.PLAIN;
                break;
            case "percent":
                mode=MODE.PERCENT;
                this.initThreshold(mode,modeThresholdArr);
                break;
            case "substract":
                mode=MODE.SUBSTRACT;
                this.initThreshold(mode,modeThresholdArr);
                break;
            case "round":
                mode=MODE.ROUND;
                this.initThreshold(mode,modeThresholdArr);
                break;
            default:
                throw new RuntimeException("Compare mode \""+modeStr+"\" is not been supported, should be one of "
                        +MODE.PERCENT.name()+", "
                        +MODE.ROUND.name()+", "
                        +MODE.SUBSTRACT.name()+", "
                        +MODE.PLAIN.name());
        }
        this.compareMode=mode;
    }
    private void initThreshold(MODE mode,String[] modeThresholdArr){
        if(modeThresholdArr.length<2){
            throw new RuntimeException("Mode "+mode.name()+" should with threshold, please update parameter with format like "+mode.name()+":1.23");
        }
        try {
            this.threshold=new BigDecimal(modeThresholdArr[1]);
        }catch (Exception e){
            throw new RuntimeException("Faile to parser threshold value \""+modeThresholdArr[1]+"\","+" the threshold should be a number and parameter should with format like "+mode.name()+":1.23");
        }
    }
    public int compare(float f1, float f2) {
        return this.compare(BigDecimal.valueOf(f1),BigDecimal.valueOf(f2));
    }

    public int compare(double d1, double d2) {
        return this.compare(BigDecimal.valueOf(d1),BigDecimal.valueOf(d2));
    }

    public int compare(BigDecimal b1, BigDecimal b2) {
        int res;
        switch (this.compareMode) {
            case PLAIN:
                res = b1.compareTo(b2);
                break;
            case ROUND:
                res = DecimalCompare.compareDoubleByRound(b1, b2, threshold.intValue());
                break;
            case PERCENT:
                res = DecimalCompare.compareByPercent(b1, b2, threshold);
                break;
            case SUBSTRACT:
                res = DecimalCompare.compareBySubstract(b1, b2, threshold);
                break;
            default:
                throw new RuntimeException("Unsupported compare mode " + this.compareMode.name());
        }
        return res;
    }

    public static int compareBySubstract(BigDecimal b1, BigDecimal b2, BigDecimal threashold) {
        BigDecimal subStractVal = b1.subtract(b2);
        if (subStractVal.abs().compareTo(threashold) <= 0) {
            return 0;
        } else if (subStractVal.compareTo(BigDecimal.ZERO) > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int compareByPercent(BigDecimal b1, BigDecimal b2, BigDecimal threashold) {
        BigDecimal subStractVal = b1.subtract(b2);
        if (subStractVal.abs().divide(b1.min(b2), BigDecimal.ROUND_HALF_UP).abs().compareTo(threashold) <= 0) {
            return 0;
        } else if (subStractVal.compareTo(BigDecimal.ZERO) > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int compareFloat(float f1, float f2) {
        return DecimalCompare.compareFloatByPrecision(f1, f2, DecimalCompare.DEFAULT_FLOAT_ROUND_THRESHOLD.intValue());
    }

    public static int compareFloatByPrecision(float f1, float f2, int precision) {
        return DecimalCompare.compareDoubleByRound(f1, f2, precision);
    }

    public static int compareDouble(double d1, double d2) {
        return DecimalCompare.compareDoubleByRound(d1, d2, 10);
    }

    public static int compareDoubleByRound(double d1, double d2, int precision) {
        double absD1 = Math.abs(d1);
        double absD2 = Math.abs(d2);
        if (absD1 < 1 && absD2 < 1) {
            return Double.compare(DoubleRounder.round(d1, precision), DoubleRounder.round(d2, precision));
        } else if (absD1 > 1 && absD2 > 1) {
            return DecimalCompare.compareDoubleByRound(BigDecimal.valueOf(d1), BigDecimal.valueOf(d2), precision);
        } else {
            return Double.compare(d1, d2);
        }

    }

    protected static int compareDoubleByRound(BigDecimal d1, BigDecimal d2, int precision) {

        if (d1.abs().compareTo(BigDecimal.ONE) < 0 && d2.abs().compareTo(BigDecimal.ONE) < 0) {
            return DecimalCompare.compareDoubleByRound(d1.doubleValue(), d2.doubleValue(), precision);
        } else if (d1.abs().compareTo(BigDecimal.ONE) > 0 && d2.abs().compareTo(BigDecimal.ONE) > 0) {
            return DecimalCompare.compareDoubleByRound(d1.divide(BigDecimal.TEN), d2.divide(BigDecimal.TEN), precision);
        } else {
            return d1.compareTo(d2);
        }

    }

}
