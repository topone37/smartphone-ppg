package com.uni.ppg.domain;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;

import org.junit.BeforeClass;

public class TestBase {

    @BeforeClass
    public static void beforeClass() {
        XLog.init(LogLevel.ALL);
    }
}
