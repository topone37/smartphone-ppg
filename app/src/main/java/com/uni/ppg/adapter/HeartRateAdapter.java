package com.uni.ppg.adapter;

import java.util.Locale;
import java.util.stream.LongStream;

/**
 * This class is responsible for converting the data points selected from the time series to
 * heart rate in BPM. The processed signal and the timestamps are fed to the adapter,
 * and the UI can be updated with the returned BPM String.
 */
public class HeartRateAdapter implements HeartRate {

    private final int[] processedSignal;
    private final long[] timestamps;

    public HeartRateAdapter(int[] processedSignal, long[] timestamps) {
        this.processedSignal = processedSignal;
        this.timestamps = timestamps;
    }

    public String convertToHeartRate() {
        long[] timesInABatch = new long[processedSignal.length - 1];
        for (int i = 0; i < processedSignal.length - 1; i++) {
            int firstIndex = processedSignal[i];
            int lastIndex = processedSignal[i + 1];
            long eta = timestamps[lastIndex] - timestamps[firstIndex];
            timesInABatch[i] = eta;
        }
        double averageHeartRate = LongStream.of(timesInABatch).average().orElse(0d);
        return uiRepresentation(averageHeartRate);
    }

    private String uiRepresentation(double averageHeartRate) {
        return String.format(Locale.ENGLISH, "%.0f BPM", averageHeartRate);
    }
}
