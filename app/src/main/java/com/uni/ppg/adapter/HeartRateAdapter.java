package com.uni.ppg.adapter;

import java.util.Locale;
import java.util.stream.LongStream;

/**
 * This class is responsible for converting the data points selected from the time series to
 * heart rate in BPM. The processed signal and the timestamps are fed to the adapter,
 * and the UI can be updated with the returned BPM String.
 */
public class HeartRateAdapter implements HeartRate {

    private final int[] zeros;
    private final long[] time;

    public HeartRateAdapter(int[] zeros, long[] time) {
        this.zeros = zeros;
        this.time = time;
    }

    public String convertToHeartRate() {
        long[] timesInABatch = new long[zeros.length - 1];
        for (int i = 0; i < zeros.length - 1; i++) {
            int firstIndex = zeros[i];
            int lastIndex = zeros[i + 1];
            long eta = time[lastIndex] - time[firstIndex];
            timesInABatch[i] = eta;
        }
        double averageHeartRate = LongStream.of(timesInABatch).average().orElse(0d);
        return uiRepresentation(averageHeartRate);
    }

    private String uiRepresentation(double averageHeartRate) {
        return String.format(Locale.ENGLISH, "%.0f BPM", averageHeartRate);
    }
}
