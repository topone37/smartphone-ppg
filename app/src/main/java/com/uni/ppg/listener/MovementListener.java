package com.uni.ppg.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import static com.uni.ppg.constant.GlobalConstants.ACCELEROMETER_LIMIT;

public class MovementListener implements SensorEventListener {

    private final TooMuchMovementCallback callback;

    public MovementListener(TooMuchMovementCallback callback) {
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
                callback.onTooMuchMovement();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
