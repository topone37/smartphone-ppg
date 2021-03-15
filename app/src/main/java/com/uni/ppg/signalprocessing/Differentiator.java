package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

/**
 * This signal processing step approximates the derivative of the
 * curve using a centered difference.
 */
public class Differentiator extends SignalProcessorChain {

    @Override
    public int[] process(int[] signal) {
        XLog.d("Running differentiation");
        int[] derivative = centeredDifference(signal);
        return processNext(derivative);
    }

    private int[] centeredDifference(int[] signal) {
        int[] derivative = new int[signal.length - 2];
        for (int t = 0; t < signal.length - 2; t++) {
            derivative[t] = (signal[t + 2] - signal[t]) / 2;
        }
        return derivative;
    }
}
