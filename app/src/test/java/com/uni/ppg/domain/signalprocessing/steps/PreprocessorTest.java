package com.uni.ppg.domain.signalprocessing.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.uni.ppg.domain.signalprocessing.pipeline.Pipeline;

import org.junit.Before;
import org.junit.Test;

public class PreprocessorTest {

    private int[] rawSignal;

    @Before
    public void setUp() {
        rawSignal = new int[]{0, 5, 10, 5, 0};
    }

    @Test
    public void canPreprocess() {
        // given
        Pipeline pipeline = new Pipeline(new Preprocessor());

        // when
        int[] processed = pipeline.execute(rawSignal);

        // then
        assertThat(processed).isEqualTo(new int[]{10, 5, 0, 5, 10});
    }

}