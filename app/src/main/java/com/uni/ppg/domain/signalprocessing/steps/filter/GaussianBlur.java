package com.uni.ppg.domain.signalprocessing.steps.filter;

import com.elvishew.xlog.XLog;
import com.uni.ppg.domain.signalprocessing.steps.Step;

public class GaussianBlur implements Step {

    private final float[] coefficients = new float[]{0.004f, 0.054f, 0.242f, 0.401f, 0.242f, 0.054f, 0.004f};
    private final int padding;

    public GaussianBlur() {
        this.padding = (coefficients.length - 1) / 2;
    }

    @Override
    public int[] invoke(int[] signal) {
        XLog.i("Applying Gaussian blur");
        return filter(signal);
    }

    private int[] filter(int[] signal) {
        int[] filtered = new int[signal.length - 2 * padding];
        for (int i = padding; i < filtered.length + padding; i++) {
            float value = 0;
            for (int j = 0; j < 7; j++) {
                value += (float) signal[i - padding + j] * coefficients[j];
            }
            filtered[i - padding] = (int) value;
        }
        return filtered;
    }
}
