package com.uni.ppg.domain.signalprocessing.steps;

import com.elvishew.xlog.XLog;
import com.uni.ppg.domain.signalprocessing.exception.FrameProcessingException;

public class ResultValidator implements Step {

    @Override
    public int[] invoke(int[] signal) {
        XLog.i("Running result validation - number of maxima: %d", signal.length);
        validate(signal);
        return signal;
    }

    private void validate(int[] signal) {
        if (signal.length < 2) {
            throw new FrameProcessingException("Not enough maxima to calculate with");
        }
    }
}