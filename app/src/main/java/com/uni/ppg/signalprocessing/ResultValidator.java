package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

public class ResultValidator extends SignalProcessorChain {

    @Override
    public int[] process(int[] signal) {
        XLog.d("Running result validation - number of maxima: %d", signal.length);
        return processNext(signal);
    }
}
