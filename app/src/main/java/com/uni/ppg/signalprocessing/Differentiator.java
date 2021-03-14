package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

/**
 * This signal processing step approximates the derivative of the
 * curve using a centered difference.
 */
public class Differentiator extends SignalProcessorChain {

    @Override
    public int[] process(int[] abundance) {
        XLog.d("Running differentiation");
        int[] derivative = centeredDifference(abundance);
        return processNext(derivative);
    }

    private int[] centeredDifference(int[] abundance) {
        int[] derivative = new int[abundance.length - 2];
        for (int t = 0; t < abundance.length - 2; t++) {
            derivative[t] = (abundance[t + 2] - abundance[t]) / 2;
        }
        return derivative;
    }
}
