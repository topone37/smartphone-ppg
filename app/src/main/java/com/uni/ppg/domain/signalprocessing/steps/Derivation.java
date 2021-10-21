package com.uni.ppg.domain.signalprocessing.steps;

import com.elvishew.xlog.XLog;

/**
 * This signal processing step approximates the derivative of the
 * curve using a centered difference.
 */
public class Derivation implements Step {

    @Override
    public int[] invoke(int[] signal) {
        XLog.i("Running differentiation");
        return centeredDifference(signal);
    }

    private int[] centeredDifference(int[] signal) {
        int[] derivative = new int[signal.length - 2];
        for (int t = 0; t < signal.length - 2; t++) {
            derivative[t] = (signal[t + 2] - signal[t]) / 2;
        }
        return derivative;
    }
}
