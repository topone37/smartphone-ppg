package com.uni.ppg.domain.adapter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class HeartRateAdapterTest {

    private static int[] signal;
    private static long[] timeStamps;

    @BeforeClass
    public static void beforeClass() throws Exception {
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