package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

/**
 * This signal processing step runs a simple low pass filter on the
 * time series, with a cutoff of 4Hz. The sampling rate
 * is approximately equal to the camera's frame processing speed in FPS.
 */
public class LowPassFilter extends SignalProcessorChain {

    private static final float ALPHA = 0.4557F;

    @Override
    public int[] process(int[] abundance) {
        XLog.d("Running low pass filter");
        return processNext(filter(abundance));
    }

    private int[] filter(int[] abundance) {
        int[] filtered = new int[abundance.length - 1];
        for (int i = 1; i < abundance.length; i++) {
            float current = ALPHA * (float) i + (1F - ALPHA) * abundance[i - 1];
            filtered[i - 1] = (int) current;
        }
        return filtered;
    }
}