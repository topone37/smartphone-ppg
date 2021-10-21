package com.uni.ppg.domain.signalprocessing.steps;

import com.elvishew.xlog.XLog;

import java.util.stream.IntStream;

/**
 * This signal processing step takes the signal after calculating the derivative,
 * and identifies the indexes where the derivative curve has a value of zero.
 * Since the it is a series of discrete data, the first point before the zero is considered.
 */
public class MaximaCalculator implements Step {

    @Override
    public int[] invoke(int[] signal) {
        XLog.i("Running maxima determination");
        return findMaxima(signal);
    }

    private int[] findMaxima(int[] firstDerivative) {
        int[] secondDerivative = difference(firstDerivative);
        return IntStream.of(findZerosIn(firstDerivative))
                .filter(i -> secondDerivative[i - 1] <= 0)
                .map(i -> i + 1) // leading point lost due to derivation
                .toArray();
    }

    private int[] difference(int[] firstDerivative) {
        Derivation derivation = new Derivation();
        return derivation.invoke(firstDerivative);
    }

    private int[] findZerosIn(int[] firstDerivative) {
        return IntStream.range(0, firstDerivative.length - 1).filter(i -> {
            int first = firstDerivative[i];
            int second = firstDerivative[i + 1];
            return (first > 0 && second < 0) || (first < 0 && second > 0) || first == 0;
        }).toArray();
    }

}
