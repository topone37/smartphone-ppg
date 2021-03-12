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
    private final int numberOfBeats;

    public HeartRateAdapter(int[] zeros, long[] time) {
        this.zeros = zeros;
        this.time = time;
        this.numberOfBeats = zeros.length - 1;
    }

    public String convertToHeartRate() {
        long[] beatsInABatch = new long[numberOfBeats];
        for (int i = 0; i < numberOfBeats; i++) {
            beatsInABatch[i] = time[zeros[i + 1]] - time[zeros[i]];
        }
        double averageHeartRate = LongStream.of(beatsInABatch).average().orElse(0d);
        return uiRepresentation(averageHeartRate);
    }

    private String uiRepresentation(double averageHeartRate) {
        return String.format(Locale.ENGLISH, "%.0f", averageHeartRate);
    }
}
