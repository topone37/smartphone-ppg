package com.uni.ppg.domain.adapter;

import java.util.Locale;
import java.util.stream.LongStream;

/**
 * This class is responsible for converting the data points selected from the time series to
 * heart rate in BPM. The processed signal and the timestamps are fed to the adapter,
 * and the UI can be updated with the returned BPM String.
 */
public class HeartRateAdapter implements HeartRate {

    private static final double MISSING_HEART_RATE = 0d;
    private static final double MILLIS_IN_A_MINUTE = 60000d;
    private static final String HEART_RATE_FORMAT = "%.0f";

    private final int[] signal;
    private final long[] timeStamps;
    private final int numberOfBeats;

    public HeartRateAdapter(int[] signal, long[] timeStamps) {
        this.signal = signal;
        this.timeStamps = timeStamps;
        this.numberOfBeats = signal.length - 1;
    }

    public String convertToHeartRate() {
        long[] beatsInABatch = calculateBeatsInABatch();
        double beatsPerMinute = convertToBPM(beatsInABatch);
        return toPrintableFormat(beatsPerMinute);
    }

    private long[] calculateBeatsInABatch() {
        long[] beatsInABatch = new long[numberOfBeats];
        for (int i = 0; i < numberOfBeats; i++) {
            beatsInABatch[i] = timeStamps[signal[i + 1]] - timeStamps[signal[i]];
        }
        return beatsInABatch;
    }

    private double convertToBPM(long[] beatsInABatch) {
        double averageHeartRateInMillis = LongStream.of(beatsInABatch).average().orElse(MISSING_HEART_RATE);
        return MILLIS_IN_A_MINUTE / averageHeartRateInMillis;
    }

    private String toPrintableFormat(double averageHeartRate) {
        return String.format(Locale.ENGLISH, HEART_RATE_FORMAT, averageHeartRate);
    }

}
