package com.uni.ppg.signalprocessing;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MaximaCalculator extends SignalProcessorChain {

    private static final int DELTA = 1000;

    @Override
    public int[] process(int[] intensities) {
        System.out.println("In maxima calculator");
        int[] zeros = findZeros(intensities);
        System.out.println(Arrays.toString(zeros));
        return processNext(zeros);
    }

    private int[] findZeros(int[] intensities) {
        return IntStream.range(0, intensities.length).filter(i -> Math.abs(intensities[i]) - DELTA < 0).toArray();
    }

}
