package com.uni.ppg.domain.signalprocessing.steps.filter;

import android.util.Log;

import com.uni.ppg.domain.signalprocessing.steps.Step;

/**
 * This signal processing step applies a 1D box filter with a desired window size to the signal.
 * The filter cannot be applied to window size -1 points.
 * For smoothing purposes.
 */
public class BoxFilter implements Step {

    private static final String TAG = BoxFilter.class.getName();

    private final int windowSize;
    private final int padding;

    public BoxFilter() {
        this.windowSize = 3;
        this.padding = 1;
    }

    @Override
    public int[] invoke(int[] signal) {
        Log.i(TAG, "Running box filter");
        return filter(signal);
    }

    private int[] filter(int[] signal) {
        int[] filtered = new int[signal.length - 2 * padding];

        for (int i = padding; i <= filtered.length; i++) {
            int sum = 0;
            for (int j = 0; j < windowSize; j++) {
                sum += signal[i - padding + j];
            }
            filtered[i - padding] = sum / windowSize;
        }

        return filtered;
    }
}
