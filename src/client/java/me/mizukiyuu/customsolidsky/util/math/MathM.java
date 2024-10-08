package me.mizukiyuu.customsolidsky.util.math;

public class MathM {

    /**
     * degree between 0 and 720.
     */
    public static final float[] SIN = new float[721];

    /**
     * degree between 0 and 720.
     */
    public static final float[] COS = new float[721];

    static {
        for (int i = 0; i <= 720; i++){
            double radians = Math.toRadians(i);
            SIN[i] = (float) Math.sin(radians);
            COS[i] = (float) Math.cos(radians);
        }
    }

    public static void swap(Object a, Object b){
        Object tmp = a;
        a = b;
        b = tmp;
    }
}
