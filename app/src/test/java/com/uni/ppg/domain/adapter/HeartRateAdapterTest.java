package com.uni.ppg.domain.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HeartRateAdapterTest {

    private int[] signal;
    private long[] timeStamps;

    @Before
    public void before() throws IOException {
        signal = new int[]{0, 25, 33, 38, 54, 66, 80};
        timeStamps = Files.lines(Paths.get("src/test/resources/time_points.txt")).mapToLong(Long::valueOf).toArray();
    }

    @Test
    public void canAdaptHeartRate() {
        // given
        HeartRate adapter = new HeartRateAdapter(signal, timeStamps);

        // when
        String heartRate = adapter.convertToHeartRate();

        // then
        assertThat(heartRate).isEqualTo("87");
    }
}