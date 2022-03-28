package com.uni.ppg.root;

import android.app.Application;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PpgApplication extends Application {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    public static ThreadPoolExecutor executor() {
        return new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }
}
