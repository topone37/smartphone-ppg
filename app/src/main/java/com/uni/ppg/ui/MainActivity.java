package com.uni.ppg.ui;

import android.annotation.SuppressLint;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Flash;
import com.uni.ppg.R;
import com.uni.ppg.domain.image.PpgFrameProcessor;

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
        ImageButton button = findViewById(R.id.btn_fingerprint);
        button.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                camera.setFlash(Flash.TORCH);
                camera.addFrameProcessor(new PpgFrameProcessor(new WeakReference<>(heartRate)));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                camera.setFlash(Flash.OFF);
                camera.clearFrameProcessors();
                heartRate.setText("");
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }

}