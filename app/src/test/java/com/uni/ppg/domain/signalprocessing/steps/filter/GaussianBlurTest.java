package com.uni.ppg.domain.signalprocessing.steps.filter;

import static org.assertj.core.api.Assertions.assertThat;

import com.uni.ppg.domain.signalprocessing.pipeline.Pipeline;
import com.uni.ppg.domain.signalprocessing.steps.RollingAverage;

import org.junit.Before;
import org.junit.Test;

public class GaussianBlurTest {

    private int[] rawSignal;

    @Before
    public void setUp() {
        rawSignal = new int[]{0, 2, 0, 2, 1, 3, 1, 3, 1, 3};
    }

    @Test
    public void canInvokeGaussianBlur() {
        // given
        Pipeline pipeline = new Pipeline(new RollingAverage(3));

        // when
        int[] processed = pipeline.execute(rawSignal);

        // then
        assertThat(processed).isEqualTo(new int[]{0, 1, 0, 1, 0, 1, 0, 1});
    }

}