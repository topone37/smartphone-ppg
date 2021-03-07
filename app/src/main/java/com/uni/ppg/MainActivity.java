package com.uni.ppg;

import android.annotation.SuppressLint;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Flash;
import com.uni.ppg.pipeline.PpgFrameProcessor;

public class MainActivity extends AppCompatActivity {

    private CameraView mCamera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCamera();
        initButton();
    }

    private void initCamera() {
        mCamera = findViewById(R.id.camera);
        mCamera.setLifecycleOwner(this);
        mCamera.setFrameProcessingFormat(ImageFormat.YUV_420_888);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButton() {
        Button button = findViewById(R.id.button);
        button.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mCamera.setFlash(Flash.TORCH);
                mCamera.addFrameProcessor(new PpgFrameProcessor());
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mCamera.setFlash(Flash.OFF);
                mCamera.clearFrameProcessors();
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.destroy();
    }

}