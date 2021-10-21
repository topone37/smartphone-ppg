package com.uni.ppg.domain.signalprocessing.pipeline;

import com.uni.ppg.domain.signalprocessing.steps.Step;

public class Pipeline {

    private final Step currentStep;

    public Pipeline(Step currentStep) {
        this.currentStep = currentStep;
    }

    public Pipeline pipe(Step next) {
        return new Pipeline(input -> next.invoke(currentStep.invoke(input)));
    }

    public int[] execute(int[] input) {
        return currentStep.invoke(input);
    }
}
