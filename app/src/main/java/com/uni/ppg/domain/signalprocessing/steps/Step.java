package com.uni.ppg.domain.signalprocessing.steps;

/**
 * Single step in a processing pipeline.
 * Does not need to be generic here, only int arrays are processed.
 */
public interface Step {
    int[] invoke(int[] input);
}
