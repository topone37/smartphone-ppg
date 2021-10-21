package com.uni.ppg.domain.signalprocessing.pipeline;

import com.uni.ppg.domain.signalprocessing.steps.Derivation;
import com.uni.ppg.domain.signalprocessing.steps.MaximaCalculator;
import com.uni.ppg.domain.signalprocessing.steps.Preprocessor;
import com.uni.ppg.domain.signalprocessing.steps.ResultValidator;
import com.uni.ppg.domain.signalprocessing.steps.RollingAverage;
import com.uni.ppg.domain.signalprocessing.steps.filter.LowPassFilter;

public class PpgProcessingPipeline {

    public static Pipeline pipeline() {
        return new Pipeline(new Preprocessor())
                .pipe(new RollingAverage())
                .pipe(new LowPassFilter(30))
                .pipe(new Derivation())
                .pipe(new MaximaCalculator())
                .pipe(new ResultValidator());
    }
}
