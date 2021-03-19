package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This signal processing step converts reflected light information to
 * absorption.
 */
public class Preprocessor extends SignalProcessorChain {

    @Override
    public int[] process(int[] signal) {
        XLog.d("Running preprocessor");
        int max = max(signal);
        int[] absorbed = IntStream.of(signal).map(i -> max - i).toArray();
        return processNext(absorbed);
    }

    private int max(int[] signal) {
        return Arrays.stream(signal).max().orElseGet(() -> {
            XLog.d("Signal does not have a maximum, preprocessing will leave it intact");
            return 0;
        });
    }
}
