package com.uni.ppg.signalprocessing;

public abstract class SignalProcessorChain {

    private SignalProcessorChain next;

    public SignalProcessorChain linkWith(SignalProcessorChain nextProcessor) {
        if (this.next == null) {
            this.next = nextProcessor;
        } else {
            this.next.next = nextProcessor;
        }
        return nextProcessor;
    }

    public abstract int[] process(int[] intensities);

    protected int[] processNext(int[] intensities) {
        if (next == null) {
            return intensities;
        }
        return next.process(intensities);
    }
}
