package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.uni.ppg.signalprocessing.filter.BoxFilter;
import com.uni.ppg.signalprocessing.filter.GaussianBlur;
import com.uni.ppg.signalprocessing.filter.LowPassFilter;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SignalProcessingTest {

    private int[] rawSignal = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @BeforeClass
    public static void beforeClass() {
        XLog.init(LogLevel.ALL);
    }

    @Test
    public void canApplyBoxFilter() {
        // given
        int padding = 1;
        SignalProcessorChain chain = new BoxFilter();

        // when
        int[] processed = chain.process(rawSignal);

        // then
        assertThat(processed).hasSize(rawSignal.length - 2 * padding);
    }

    @Test
    public void canApplyLowPassFilter() {
        // given
        SignalProcessorChain chain = new LowPassFilter(30);

        // when
        int[] processed = chain.process(rawSignal);

        // then
        assertThat(processed).hasSize(9).doesNotContain(10);
    }

    @Test
    public void canApplyGaussianBlur() {
        // given
        SignalProcessorChain chain = new GaussianBlur();

        // when
        int[] processed = chain.process(rawSignal);

        // then
        assertThat(processed).hasSize(4);
    }

    @Test
    public void canApplyRollingAverageSubtraction() {
        // given
        SignalProcessorChain chain = new RollingAverage(3);

        // when
        int[] processed = chain.process(rawSignal);

        // then
        assertThat(processed).hasSize(8).containsOnly(1);
    }
}