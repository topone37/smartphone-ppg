package com.uni.ppg.domain.signalprocessing.steps;

import com.uni.ppg.domain.TestBase;
import com.uni.ppg.domain.signalprocessing.pipeline.Pipeline;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DerivationTest extends TestBase {

    private int[] rawSignal;

    @Before
    public void setUp() {
        rawSignal = new int[]{0, 5, 10, 5, 0};
    }

    @Test
    public void canInvokeDerivation() {
        // given
        Pipeline pipeline = new Pipeline(new Derivation());

        // when
        int[] processed = pipeline.execute(rawSignal);

        // then
        assertThat(processed).isEqualTo(new int[]{5, 0, -5});
    }
}