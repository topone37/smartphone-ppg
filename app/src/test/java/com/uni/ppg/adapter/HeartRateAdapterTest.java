package com.uni.ppg.adapter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class HeartRateAdapterTest {

    // values from real measured data
    private static int[] zeros;
    private static long[] time;

    @BeforeClass
    public static void beforeClass() throws Exception {
        zeros = new int[]{0, 25, 33, 38, 54, 66, 80};
        time = Files.lines(Paths.get("src/test/resources/time_points.txt")).mapToLong(Long::valueOf).toArray();
    }

    @Test
    public void canAdaptHeartRate() {
        // given
        HeartRate adapter = new HeartRateAdapter(zeros, time);

        // when
        String heartRate = adapter.convertToHeartRate();

        // then
        assertThat(heartRate).isEqualTo("87");
    }
}