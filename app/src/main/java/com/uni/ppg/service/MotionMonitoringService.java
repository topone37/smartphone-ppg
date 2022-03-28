package com.uni.ppg.service;

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
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.uni.ppg.R;
import com.uni.ppg.constant.GlobalConstants;

public class MotionMonitoringService extends Service {

    private static final String TAG = MotionMonitoringService.class.getName();

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
        listenToBroadcast();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initAccelerationSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void listenToSensor() {
        if (acceleration != null) {
            motionListener = new MotionListener(() -> Toast.makeText(this, R.string.toast_keep_device_still, Toast.LENGTH_SHORT).show());
            sensorManager.registerListener(motionListener, acceleration, SensorManager.SENSOR_DELAY_NORMAL);
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

    public class PhaseChangedBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(GlobalConstants.MEASUREMENT_PHASE_CHANGE);
            if (message != null) {
                Log.i(TAG, "Measurement phase changed to: " + message);
                if (MeasurementPhase.valueOf(message) == MeasurementPhase.START) {
                    listenToSensor();
                } else {
                    stopSensorListener();
                }
            }
        }
    }

}
