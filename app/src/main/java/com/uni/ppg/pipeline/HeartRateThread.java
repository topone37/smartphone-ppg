package com.uni.ppg.pipeline;

import com.elvishew.xlog.XLog;


public class HeartRateThread implements Runnable {

    private final int[] intensities;
    private final long[] timestamps;

    public HeartRateThread(int[] intensities, long[] timestamps) {
        this.intensities = intensities;
        this.timestamps = timestamps;
    }

    @Override
    public void run() {
        XLog.d("Thread fired");
    }
}
