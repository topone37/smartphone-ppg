package com.uni.ppg.signalprocessing;

public class ResultValidator extends SignalProcessorChain {

    @Override
    public int[] process(int[] intensities) {
        System.out.println("In result validator");
        return processNext(intensities);
    }
}
