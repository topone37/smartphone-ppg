package com.uni.ppg.signalprocessing;

public abstract class SignalProcessorChain {

    private SignalProcessorChain next;

    public SignalProcessorChain linkWith(SignalProcessorChain nextProcessor) {
        if (this.next == null) {
            this.next = nextProcessor;
        }
        return nextProcessor;
    }

    protected int[] processNext(int[] abundance) {
        if (next == null) {
            return abundance;
        }
        return next.process(abundance);
    }

    public abstract int[] process(int[] intensities);
}
