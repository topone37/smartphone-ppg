package com.uni.ppg.signalprocessing;

import java.util.Arrays;

import static com.uni.ppg.constant.GlobalConstants.BATCH_SIZE;

public class Derivator extends SignalProcessorChain {

    @Override
    public int[] process(int[] intensities) {
        int[] derivative = centeredDifferenceQuotient(intensities);
        System.out.println(Arrays.toString(derivative));
        return processNext(derivative);
    }

    private int[] centeredDifferenceQuotient(int[] intensities) {
        int[] derivative = new int[BATCH_SIZE];
        for (int t = 1; t < BATCH_SIZE - 1; t++) {
            derivative[t - 1] = (intensities[t + 1] - intensities[t - 1]) / 2;
        }
        return derivative;
    }
}
