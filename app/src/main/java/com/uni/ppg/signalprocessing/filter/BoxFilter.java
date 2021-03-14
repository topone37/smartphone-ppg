package com.uni.ppg.signalprocessing.filter;

import com.elvishew.xlog.XLog;
import com.uni.ppg.signalprocessing.SignalProcessorChain;

import java.util.Arrays;

/**
 * This signal processing step applies a 1D box filter with a desired window size to the signal.
 * The filter cannot be applied to window size -1 points.
 * For smoothing purposes.
 */
public class BoxFilter extends SignalProcessorChain {

    private final int windowSize;
    private final int padding;

    public BoxFilter(int windowSize) {
        this.windowSize = windowSize;
        this.padding = (windowSize - 1) / 2;
    }

    public BoxFilter() {
        this.windowSize = 3;
        this.padding = 1;
    }

    @Override
    public int[] process(int[] abundance) {
        XLog.d("Applying box filter with window size: %d", windowSize);
        int[] filtered = filter(abundance);
        return processNext(filtered);
    }

    private int[] filter(int[] abundance) {
        int[] filtered = new int[abundance.length - 2 * padding];
        for (int i = padding; i <= filtered.length; i++) {
            int sum = 0;
            for (int j = 0; j < windowSize; j++) {
                sum += abundance[i - padding + j];
            }
            filtered[i - padding] = sum / windowSize;
        }
        return filtered;
    }
}
