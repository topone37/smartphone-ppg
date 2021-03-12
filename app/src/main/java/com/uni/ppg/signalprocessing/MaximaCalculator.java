package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

import java.util.stream.IntStream;

/**
 * This signal processing step takes the signal after calculating the derivative,
 * and identifies the indexes where the derivative curve has a value of zero.
 * Since the it is a series of discrete data, everything under a DELTA value
 * is identified as zero.
 * The DELTA is empirical, the optimal value varies.
 */
public class MaximaCalculator extends SignalProcessorChain {

    private static final int DELTA = 1000;

    @Override
    public int[] process(int[] intensities) {
        XLog.d("Running maxima determination");
        int[] zeros = findZeros(intensities);
        return processNext(zeros);
    }

    private int[] findZeros(int[] intensities) {
        return IntStream.range(0, intensities.length).filter(i -> Math.abs(intensities[i]) - DELTA < 0).toArray();
    }

}
