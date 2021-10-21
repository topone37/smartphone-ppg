package com.uni.ppg.domain.signalprocessing.filter;

import com.elvishew.xlog.XLog;
import com.uni.ppg.domain.signalprocessing.SignalProcessorChain;

import uk.me.berndporr.iirj.Butterworth;

/**
 * This signal processing step runs a low pass Butterworth filter on the
 * time series, with a cutoff of 4Hz. The sampling rate
 * is approximately equal to the camera's frame processing speed in FPS.
 */
public class LowPassFilter extends SignalProcessorChain {

    private final Butterworth butterworth = new Butterworth();

    public LowPassFilter(int fps) {
        this.butterworth.lowPass(1, fps, 4);
    }

    @Override
    public int[] process(int[] signal) {
        XLog.d("Running low pass filter");
        int[] filtered = filter(signal);
        return processNext(filtered);
    }

    private int[] filter(int[] signal) {
        int[] filtered = new int[signal.length - 1];
        for (int i = 0; i < signal.length; i++) {
            filtered[i] = (int) butterworth.filter(signal[i]);
        }
        return filtered;
    }
}