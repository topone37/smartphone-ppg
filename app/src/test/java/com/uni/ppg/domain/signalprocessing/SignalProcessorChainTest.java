package com.uni.ppg.domain.signalprocessing;

import com.uni.ppg.domain.TestBase;
import com.uni.ppg.domain.signalprocessing.pipeline.Pipeline;
import com.uni.ppg.domain.signalprocessing.pipeline.PpgProcessingPipeline;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class SignalProcessorChainTest extends TestBase {

    private int[] rawSignal;

    @Before
    public void before() throws IOException {
        rawSignal = Files.lines(Paths.get("src/test/resources/data_points.txt")).mapToInt(Integer::valueOf).toArray();
    }

    @Test
    public void canApplyConsecutiveProcessingSteps() {
        // given
        Pipeline pipeline = PpgProcessingPipeline.pipeline();

        // when
        int[] processed = pipeline.execute(rawSignal);

        // then
        assertThat(processed).hasSize(6);
    }
}
