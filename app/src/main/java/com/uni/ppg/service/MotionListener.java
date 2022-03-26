package com.uni.ppg.service;

import static com.uni.ppg.constant.GlobalConstants.ACCELEROMETER_LIMIT;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * This sensor event listener monitors the acceleration of the phone in all 3 directions.
 * If the total acceleration exceeds a threshold, a callback is called to inform the user.
 */
public class MotionListener implements SensorEventListener {

    private final ExcessiveMotionCallback callback;

    public MotionListener(ExcessiveMotionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float total = (float) Math.sqrt(x * x + y * y + z * z);
            if (total > ACCELEROMETER_LIMIT) {
                callback.onExcessiveMotion();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
