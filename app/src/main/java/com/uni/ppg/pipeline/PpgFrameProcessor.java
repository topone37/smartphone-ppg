package com.uni.ppg.pipeline;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.size.Size;
import com.uni.ppg.ImageProcessor;

import static com.uni.ppg.PpgApplication.executor;

public class PpgFrameProcessor implements com.otaliastudios.cameraview.frame.FrameProcessor {

    private static final int BATCH_SIZE = 50;

    private int counter;
    private int[] intensities;
    private long[] timestamps;

    public PpgFrameProcessor() {
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
            startPipelineThread();
            resetParameters();
        }
    }

    private void startPipelineThread() {
        executor().execute(new HeartRateThread(intensities, timestamps));
    }

    private void resetParameters() {
        counter = 0;
        intensities = new int[BATCH_SIZE];
        timestamps = new long[BATCH_SIZE];
    }

}
