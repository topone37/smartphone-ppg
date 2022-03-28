package com.uni.ppg.domain.signalprocessing.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.uni.ppg.domain.signalprocessing.exception.FrameProcessingException;
import com.uni.ppg.domain.signalprocessing.pipeline.Pipeline;

import org.junit.Before;
import org.junit.Test;

public class ResultValidatorTest {

    private int[] maxima;

    @Before
    public void setUp() {
        maxima = new int[]{0, 10};
    }

    @Test
    public void canValidateResult() {
        // given
        Pipeline pipeline = new Pipeline(new ResultValidator());

        // when
        int[] processed = pipeline.execute(maxima);

        // then
        assertThat(processed).isEqualTo(new int[]{0, 10});
    }

    @Test(expected = FrameProcessingException.class)
    public void whenNotEnoughMaximaPoints_thenThrowCustomException() {
        Pipeline pipeline = new Pipeline(new ResultValidator());
        pipeline.execute(new int[]{0});
    }
}