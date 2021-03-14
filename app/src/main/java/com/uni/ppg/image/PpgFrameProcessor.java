package com.uni.ppg.image;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;
import com.otaliastudios.cameraview.size.Size;
import com.uni.ppg.adapter.HeartRate;
import com.uni.ppg.adapter.HeartRateAdapter;
import com.uni.ppg.signalprocessing.Differentiator;
import com.uni.ppg.signalprocessing.MaximaCalculator;
import com.uni.ppg.signalprocessing.ResultValidator;
import com.uni.ppg.signalprocessing.SignalProcessorChain;
import com.uni.ppg.signalprocessing.filter.BoxFilter;
import com.uni.ppg.signalprocessing.filter.GaussianBlur;

import java.lang.ref.WeakReference;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.uni.ppg.PpgApplication.executor;
import static com.uni.ppg.constant.GlobalConstants.BATCH_SIZE;

public class PpgFrameProcessor implements FrameProcessor {

    /**
     * The number of frames collected for a single batch
     */
    private int frameCounter;

    /**
     * Y axis: the amount of color Red
     */
    private int[] abundance;

    /**
     * X axis: current time in milliseconds when a frame is captured
     */
    private long[] time;

    /**
     * Reference to the UI element displaying heart rate
     */
    private WeakReference<TextView> viewWeakReference;

    public PpgFrameProcessor(WeakReference<TextView> viewWeakReference) {
        this.viewWeakReference = viewWeakReference;
        resetParameters();
    }

    @Override
    @WorkerThread
    public void process(@NonNull Frame frame) {
        Size size = frame.getSize();
        int redSum = PixelProcessor.yuvToRedSum(frame.getData(), size.getWidth(), size.getHeight());
        abundance[frameCounter] = redSum;
        time[frameCounter] = frame.getTime();

        if (++frameCounter == BATCH_SIZE) {
            calculateHeartRate();
            resetParameters();
        }
    }

    private void calculateHeartRate() {
        long startTime = time[0];
        int[] y = IntStream.of(abundance).toArray();
        long[] x = LongStream.of(time).map(t -> t - startTime).toArray();

        CompletableFuture.supplyAsync(() -> processSignal(y), executor())
                .thenApply(signal -> toHeartRate(signal, x))
                .thenAccept(this::updateUI);
    }

    private int[] processSignal(int[] unprocessedSignal) {
        SignalProcessorChain chain = new BoxFilter();
        chain.linkWith(new GaussianBlur())
                .linkWith(new Differentiator())
                .linkWith(new MaximaCalculator())
                .linkWith(new ResultValidator());
        return chain.process(unprocessedSignal);
    }

    private String toHeartRate(int[] processedSignal, long[] timestamps) {
        HeartRate adapter = new HeartRateAdapter(processedSignal, timestamps);
        return adapter.convertToHeartRate();
    }

    private void updateUI(String heartRate) {
        TextView textView = viewWeakReference.get();
        textView.post(() -> textView.setText(heartRate));
    }

    private void resetParameters() {
        frameCounter = 0;
        abundance = new int[BATCH_SIZE];
        time = new long[BATCH_SIZE];
    }
}
