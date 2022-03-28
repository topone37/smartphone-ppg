package com.uni.ppg.domain.signalprocessing.steps.filter;

import static org.assertj.core.api.Assertions.assertThat;

import com.uni.ppg.domain.signalprocessing.pipeline.Pipeline;

import org.junit.Before;
import org.junit.Test;

public class BoxFilterTest {

    private int[] rawSignal;

    @Before
    public void setUp() {
        rawSignal = new int[]{0, 2, 0, 2, 1, 3, 1, 3, 1, 3};
    }

    @Test
    public void canInvokeBoxFilter() {
        // given
        Pipeline pipeline = new Pipeline(new BoxFilter());

        // when
        int[] processed = pipeline.execute(rawSignal);

        // then
        assertThat(processed).isEqualTo(new int[]{0, 1, 1, 2, 1, 2, 1, 2});
    }

}