package com.uni.ppg.signalprocessing;

import com.elvishew.xlog.XLog;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This signal processing step takes the signal after calculating the derivative,
 * and identifies the indexes where the derivative curve has a value of zero.
 * Since the it is a series of discrete data, the first point before the zero is considered.
 */
public class MaximaCalculator extends SignalProcessorChain {

    @Override
    public int[] process(int[] signal) {
        XLog.d("Running maxima determination");
        int[] maxima = findMaxima(signal);
        return processNext(maxima);
    }

    private int[] findMaxima(int[] firstDerivative) {
        int[] secondDerivative = difference(firstDerivative);
        return IntStream.of(findZerosIn(firstDerivative)).filter(i -> secondDerivative[i] <= 0).toArray();
    }

    private int[] difference(int[] firstDerivative) {
        Differentiator differentiator = new Differentiator();
        return differentiator.process(firstDerivative);
    }

    private int[] findZerosIn(int[] firstDerivative) {
        return IntStream.range(0, firstDerivative.length - 1).filter(i -> {
            int first = firstDerivative[i];
            int second = firstDerivative[i + 1];
            return (first > 0 && second < 0) || (first < 0 && second > 0) || first == 0;
        }).toArray();
    }

}
