package com.uni.ppg.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.uni.ppg.constant.GlobalConstants;

public class PhaseChangedBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.i("Measurement phase changed to: " + intent.getStringExtra(GlobalConstants.MEASUREMENT_PHASE_CHANGE));
    }

}
