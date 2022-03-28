package com.uni.ppg.domain.signalprocessing.steps;

import android.util.Log;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This signal processing step converts reflected light information to
 * absorption.
 */
public class Preprocessor implements Step {

    private static final String TAG = Preprocessor.class.getName();

    @Override
    public int[] invoke(int[] signal) {
        Log.i(TAG, "Running preprocessor");
        int max = max(signal);
        return IntStream.of(signal).map(i -> max - i).toArray();
    }

    private int max(int[] signal) {
        return Arrays.stream(signal).max().orElseGet(() -> {
            Log.i(TAG, "Signal does not have a maximum, preprocessing will leave it intact");
            return 0;
        });
    }
}
