package com.uni.ppg.signalprocessing.filter;

import com.elvishew.xlog.XLog;
import com.uni.ppg.signalprocessing.SignalProcessorChain;

/**
 * This signal processing step runs a simple low pass filter on the
 * time series, with a cutoff of 4Hz. The sampling rate
 * is approximately equal to the camera's frame processing speed in FPS.
 */
public class LowPassFilter extends SignalProcessorChain {

    private static final float ALPHA = 0.4557F;

    @Override
    public int[] process(int[] signal) {
        XLog.d("Running low pass filter");
        return processNext(filter(signal));
    }

    private int[] filter(int[] signal) {
        int[] filtered = new int[signal.length - 1];
        for (int i = 1; i < signal.length; i++) {
            float current = ALPHA * (float) i + (1F - ALPHA) * signal[i - 1];
            filtered[i - 1] = (int) current;
        }
        return filtered;
    }
}