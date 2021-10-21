package com.uni.ppg.domain.signalprocessing.steps;

import com.elvishew.xlog.XLog;

import java.util.Arrays;

/**
 * This signal processing step is responsible for detrending the signal, using
 * the subtraction of a rolling average with a desired window size.
 * The rolling AVG is calculated from a number of points equal to the windowsSize.
 * Default window size 10.
 */
public class RollingAverage implements Step {

    private final int windowSize;

    public RollingAverage(int windowSize) {
        this.windowSize = windowSize;
    }

    public RollingAverage() {
        this.windowSize = 10;
    }

    @Override
    public int[] invoke(int[] signal) {
        XLog.i("Running rolling average subtraction");
        return subtractAverage(signal);
    }

    /**
     * Calculating and subtracting rolling average.
     * windowSize -1 points are lost at the beginning of the signal due to average calculation.
     *
     * @param signal preprocessed signal
     * @return detrended signal
     */
    private int[] subtractAverage(int[] signal) {
        int[] remainingPoints = Arrays.stream(signal, windowSize - 1, signal.length).toArray();
        for (int i = 0; i <= signal.length - windowSize; i++) {
            double average = Arrays.stream(signal, i, i + windowSize).average().orElse(signal[i]);
            remainingPoints[i] = remainingPoints[i] - (int) average;
        }
        return remainingPoints;
    }
}
