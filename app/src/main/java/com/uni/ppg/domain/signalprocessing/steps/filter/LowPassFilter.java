package com.uni.ppg.domain.signalprocessing.steps.filter;

import android.util.Log;

import com.uni.ppg.domain.signalprocessing.steps.Step;

import uk.me.berndporr.iirj.Butterworth;

/**
 * This signal processing step runs a low pass Butterworth filter on the
 * time series, with a cutoff of 4Hz. The sampling rate
 * is approximately equal to the camera's frame processing speed in FPS.
 */
public class LowPassFilter implements Step {

    private static final String TAG = LowPassFilter.class.getName();

    private final Butterworth butterworth = new Butterworth();

    public LowPassFilter(int fps) {
        this.butterworth.lowPass(1, fps, 4);
    }

    @Override
    public int[] invoke(int[] signal) {
        Log.i(TAG, "Running low pass filter");
        return filter(signal);
    }

    private int[] filter(int[] signal) {
        int[] filtered = new int[signal.length];
        for (int i = 0; i < signal.length; i++) {
            filtered[i] = (int) butterworth.filter(signal[i]);
        }
        return filtered;
    }
}