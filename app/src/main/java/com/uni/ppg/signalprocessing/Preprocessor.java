package com.uni.ppg.signalprocessing;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Preprocessor extends SignalProcessorChain {

    @Override
    public int[] process(int[] signal) {
        int max = max(signal);
        int[] absorbed = IntStream.of(signal).map(i -> max - i).toArray();
        return processNext(absorbed);
    }

    private int max(int[] signal) {
        return Arrays.stream(signal).max().orElse(0);
    }
}
