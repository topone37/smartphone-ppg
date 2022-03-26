package com.uni.ppg.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Flash;
import com.uni.ppg.R;
import com.uni.ppg.constant.GlobalConstants;
import com.uni.ppg.domain.image.PpgFrameProcessor;
import com.uni.ppg.listener.MeasurementPhase;
import com.uni.ppg.listener.MotionMonitoringService;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private CameraView camera;
    private TextView heartRate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCamera();
        initButton();
    }

    private void initCamera() {
        camera = findViewById(R.id.view_camera);
        camera.setVisibility(View.INVISIBLE);
        camera.setLifecycleOwner(this);
        camera.setFrameProcessingFormat(ImageFormat.YUV_420_888);
        heartRate = findViewById(R.id.text_heart_rate);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButton() {
        findViewById(R.id.btn_start_measurement).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startMeasurement();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                stopMeasurement();
            }
            return false;
        });
    }

    /**
     * This background service monitors the motion of the phone
     * The phone needs to be still to do precise measurements
     */
    private Intent motionMonitoring() {
        return new Intent(MainActivity.this, MotionMonitoringService.class);
    }

    /**
     * Starting heart rate measurement:
     * - flash is turned on
     * - processing consecutive frames starts
     * - label showing heart rate will later be updated according to new results
     * - start monitoring movement of the phone
     */
    private void startMeasurement() {
        camera.setFlash(Flash.TORCH);
        camera.addFrameProcessor(new PpgFrameProcessor(new WeakReference<>(heartRate)));
        motionMonitoring(MeasurementPhase.START);
    }

    /**
     * Stopping heart rate measurement:
     * - flash is turned off
     * - processing frames stops
     * - label showing heart rate is cleared
     * - start monitoring movement of the phone
     */
    private void stopMeasurement() {
        camera.setFlash(Flash.OFF);
        camera.clearFrameProcessors();
        heartRate.setText(R.string.label_empty);
        motionMonitoring(MeasurementPhase.STOP);
    }

    private void motionMonitoring(MeasurementPhase phase) {
        Intent intent = new Intent(GlobalConstants.MEASUREMENT_PHASE_CHANGE);
        intent.putExtra(GlobalConstants.MEASUREMENT_PHASE_CHANGE, phase.name());
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.open();
        startService(motionMonitoring());
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.close();
        stopService(motionMonitoring());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.destroy();
        stopService(motionMonitoring());
    }

}