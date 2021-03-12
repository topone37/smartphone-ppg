package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

import static com.uni.ppg.constant.GlobalConstants.BATCH_SIZE;

/**
 * This signal processing step approximates the derivative of the
 * curve using a centered difference.
 */
public class Differentiator extends SignalProcessorChain {

    @Override
    public int[] process(int[] abundance) {
        XLog.d("Running differentiation");
        int[] derivative = centeredDifferenceQuotient(abundance);
        return processNext(derivative);
    }

    private int[] centeredDifferenceQuotient(int[] abundance) {
        int[] derivative = new int[BATCH_SIZE - 1];
        for (int t = 1; t < BATCH_SIZE - 2; t++) {
            derivative[t - 1] = (abundance[t + 1] - abundance[t - 1]) / 2;
        }
        return derivative;
    }
}
