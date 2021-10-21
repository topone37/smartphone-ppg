package com.uni.ppg.domain.adapter;

import java.util.Locale;
import java.util.stream.LongStream;

/**
 * This class is responsible for converting the data points selected from the time series to
 * heart rate in BPM. The processed signal and the timestamps are fed to the adapter,
 * and the UI can be updated with the returned BPM String.
 */
public class HeartRateAdapter implements HeartRate {

    private final int[] timeSeries;
    private final long[] timeStamps;
    private final int numberOfBeats;

    public HeartRateAdapter(int[] timeSeries, long[] timeStamps) {
        this.timeSeries = timeSeries;
        this.timeStamps = timeStamps;
        this.numberOfBeats = timeSeries.length - 1;
    }

    public String convertToHeartRate() {
        long[] beatsInABatch = beatsInABatch();
        double beatsPerMinute = convertToBeatsPerMinute(beatsInABatch);
        return toPrintableFormat(beatsPerMinute);
    }

    private long[] beatsInABatch() {
        long[] beatsInABatch = new long[numberOfBeats];
        for (int i = 0; i < numberOfBeats; i++) {
            beatsInABatch[i] = timeStamps[timeSeries[i + 1]] - timeStamps[timeSeries[i]];
        }
        return beatsInABatch;
    }

    private double convertToBeatsPerMinute(long[] beatsInABatch) {
        double averageHeartRateInMillis = LongStream.of(beatsInABatch).average().orElse(0d);
        return 60000d / averageHeartRateInMillis;
    }

    private String toPrintableFormat(double averageHeartRate) {
        return String.format(Locale.ENGLISH, "%.0f", averageHeartRate);
    }
}
