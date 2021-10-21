package com.uni.ppg.constant;

public class GlobalConstants {

    /**
     * The number of camera frames processed in one batch
     */
    public static final int BATCH_SIZE = 100;

    /**
     * The sum of movement across the 3 axes above which 'too much movement' applies
     */
    public static final float ACCELEROMETER_LIMIT = 10F;
}
