package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

public class ResultValidator extends SignalProcessorChain {

    @Override
    public int[] process(int[] intensities) {
        XLog.d("Validating results. Number of maxima found: %d", intensities.length);
        return processNext(intensities);
    }
}
