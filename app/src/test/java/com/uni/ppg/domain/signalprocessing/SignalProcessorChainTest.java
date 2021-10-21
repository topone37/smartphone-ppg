package com.uni.ppg.domain.signalprocessing;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.uni.ppg.domain.signalprocessing.filter.BoxFilter;
import com.uni.ppg.domain.signalprocessing.filter.GaussianBlur;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class SignalProcessorChainTest {

    private static int[] rawSignal;

    @BeforeClass
    public static void beforeClass() throws IOException {
        XLog.init(LogLevel.ALL);
        rawSignal = Files.lines(Paths.get("src/test/resources/data_points.txt")).mapToInt(Integer::valueOf).toArray();
    }

    @Test
    public void canApplyConsecutiveProcessingSteps() {
        // given
        SignalProcessorChain chain = new BoxFilter();
        chain.linkWith(new GaussianBlur()).linkWith(new Differentiator()).linkWith(new MaximaCalculator());

        // when
        int[] processed = chain.process(rawSignal);

        // then
        assertThat(processed).hasSize(6);
    }
}
