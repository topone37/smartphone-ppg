package com.uni.ppg.root;

import android.app.Application;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PpgApplication extends Application {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    @Override
    public void onCreate() {
        super.onCreate();
        XLog.init(LogLevel.ALL);
    }

    public static ThreadPoolExecutor executor() {
        return new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }
}
