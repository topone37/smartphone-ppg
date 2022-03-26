package com.uni.ppg.listener;

import static com.uni.ppg.constant.GlobalConstants.MEASUREMENT_PHASE_CHANGE;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;

public class MotionMonitoringService extends Service {

    static {
        XLog.init(LogLevel.ALL);
    }

    private BroadcastReceiver broadcastReceiver;
    private SensorManager sensorManager;
    private Sensor acceleration;
    private SensorEventListener motionListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initAccelerationSensor();
        listenToSensor();
        listenToBroadcast();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initAccelerationSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void listenToSensor() {
        if (acceleration != null) {
            motionListener = new MotionListener(() -> Toast.makeText(this, "Stay still", Toast.LENGTH_SHORT).show());
            sensorManager.registerListener(motionListener, acceleration, SensorManager.SENSOR_DELAY_NORMAL);
            XLog.i("Sensor listener registered");
        }
    }

    private void listenToBroadcast() {
        broadcastReceiver = new PhaseChangedBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(MEASUREMENT_PHASE_CHANGE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSensorListener();
        unregisterReceiver(broadcastReceiver);
    }

    private void stopSensorListener() {
        if (acceleration != null) {
            sensorManager.unregisterListener(motionListener);
        }
    }

}
