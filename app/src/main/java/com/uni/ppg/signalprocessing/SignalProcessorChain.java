package com.uni.ppg.signalprocessing;

public abstract class SignalProcessorChain {

    private SignalProcessorChain next;

    public SignalProcessorChain linkWith(SignalProcessorChain nextProcessor) {
        if (this.next == null) {
            this.next = nextProcessor;
        }
        return nextProcessor;
    }

    protected int[] processNext(int[] signal) {
        if (next == null) {
            return signal;
        }
        return next.process(signal);
    }

    public abstract int[] process(int[] signal);
}
