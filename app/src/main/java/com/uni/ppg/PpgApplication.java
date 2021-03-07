package com.uni.ppg;

import android.app.Application;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PpgApplication extends Application {

    static {
        XLog.init(LogLevel.ALL);
    }

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            NUMBER_OF_CORES,
            NUMBER_OF_CORES,
            1,
            TimeUnit.SECONDS,
            WORK_QUEUE
    );

    public static ThreadPoolExecutor executor() {
        return EXECUTOR;
    }

}
