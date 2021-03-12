package com.uni.ppg.image;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;
import com.otaliastudios.cameraview.size.Size;
import com.uni.ppg.adapter.HeartRate;
import com.uni.ppg.adapter.HeartRateAdapter;
import com.uni.ppg.signalprocessing.Derivator;
import com.uni.ppg.signalprocessing.LowPassFilter;
import com.uni.ppg.signalprocessing.MaximaCalculator;
import com.uni.ppg.signalprocessing.ResultValidator;
import com.uni.ppg.signalprocessing.SignalProcessorChain;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static com.uni.ppg.PpgApplication.executor;
import static com.uni.ppg.constant.GlobalConstants.BATCH_SIZE;

public class PpgFrameProcessor implements FrameProcessor {

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
        int redSum = PixelProcessor.yuvToRedSum(frame.getData(), size.getWidth(), size.getHeight());
        intensities[counter] = redSum;
        timestamps[counter] = frame.getTime();
        System.out.println(redSum);

        if (++counter == BATCH_SIZE) {
            System.out.println("FIRING THREAD");
            calculateHeartRate();
            resetParameters();
        }
    }

    private void calculateHeartRate() {
        int[] rawIntensities = IntStream.of(intensities).toArray();
        CompletableFuture.supplyAsync(() -> processSignal(rawIntensities), executor())
                .thenApply(this::toHeartRate)
                .thenAccept(this::updateUI);
    }

    private void updateUI(String heartRate) {
        System.out.println("Updating UI");
        TextView textView = viewWeakReference.get();
        textView.post(() -> textView.setText(heartRate));
    }

    private String toHeartRate(int[] processedSignal) {
        System.out.println("Adapting signal");
        HeartRate adapter = new HeartRateAdapter(processedSignal, timestamps);
        return adapter.convertToHeartRate();
    }

    private int[] processSignal(int[] rawIntensities) {
        System.out.println(Arrays.toString(rawIntensities));
        SignalProcessorChain chain = new LowPassFilter();
        chain.linkWith(new Derivator()).linkWith(new MaximaCalculator()).linkWith(new ResultValidator());
        return chain.process(rawIntensities);
    }

    private void resetParameters() {
        counter = 0;
        intensities = new int[BATCH_SIZE];
        timestamps = new long[BATCH_SIZE];
    }
}
