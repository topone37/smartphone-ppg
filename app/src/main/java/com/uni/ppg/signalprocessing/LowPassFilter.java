package com.uni.ppg.signalprocessing;

public class LowPassFilter extends SignalProcessorChain {

    // LPF: Fc = 4 HZ, sampling rate = 30 HZ
    private static final float ALPHA = 0.4557F;

    @Override
    public int[] process(int[] intensities) {
        return processNext(filter(intensities));
    }

    private int[] filter(int[] intensities) {
        int[] filtered = new int[intensities.length - 1];
        for (int i = 1; i < intensities.length; i++) {
            float current = ALPHA * (float) i + (1F - ALPHA) * intensities[i - 1];
            filtered[i - 1] = (int) current;
        }
        return filtered;
    }
}