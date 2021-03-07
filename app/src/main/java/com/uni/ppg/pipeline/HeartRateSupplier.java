package com.uni.ppg.pipeline;

import java.util.function.Supplier;

public class HeartRateSupplier implements Supplier<String> {

    private final int[] intensities;
    private final long[] timestamps;

    public HeartRateSupplier(int[] intensities, long[] timestamps) {
        this.intensities = intensities;
        this.timestamps = timestamps;
    }

    @Override
    public String get() {
        return timestamps[0] + "," + intensities[0];
    }
}
