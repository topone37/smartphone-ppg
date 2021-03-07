package com.uni.ppg.pipeline;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.size.Size;
import com.uni.ppg.ImageProcessor;

import java.lang.ref.WeakReference;
import java.util.concurrent.CompletableFuture;

import static com.uni.ppg.PpgApplication.executor;

public class PpgFrameProcessor implements com.otaliastudios.cameraview.frame.FrameProcessor {

    private static final int BATCH_SIZE = 100;

    private int counter;
    private int[] intensities;
    private long[] timestamps;
    private WeakReference<TextView> viewWeakReference;

    public PpgFrameProcessor(WeakReference<TextView> viewWeakReference) {
        this.viewWeakReference = viewWeakReference;
        resetParameters();
    }

    @Override
    @WorkerThread
    public void process(@NonNull Frame frame) {
        Size size = frame.getSize();
        int avg = ImageProcessor.yuvToRedSum(frame.getData(), size.getWidth(), size.getHeight());
        intensities[counter] = avg;
        timestamps[counter] = frame.getTime();
        counter++;

        if (counter == BATCH_SIZE) {
            calculateHeartRate();
            resetParameters();
        }
    }

    private void calculateHeartRate() {
        CompletableFuture.supplyAsync(new HeartRateSupplier(intensities, timestamps), executor()).thenAccept(s -> {
            TextView textView = viewWeakReference.get();
            textView.post(() -> textView.setText(s));
        });
    }

    private void resetParameters() {
        counter = 0;
        intensities = new int[BATCH_SIZE];
        timestamps = new long[BATCH_SIZE];
    }

}
