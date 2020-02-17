package com.mambure.chuckjokes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class JokeService extends Worker {

    public JokeService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    static void enqeue(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true).build();

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                JokeService.class,
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .setInitialDelay(20000, TimeUnit.MILLISECONDS)
                .addTag("Joke work")
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);
    }

    @NonNull
    @Override
    public Result doWork() {
         String response = HttpRequestUtil.makeRequest(MainActivity.JOKES_SERVER_URL);
        JokeNotification.notify(getApplicationContext(), response);

        if (response != null && !response.isEmpty()) {
            return Result.success();
        }
        return Result.failure();
    }

    public static void cancel(Context context) {
        WorkManager.getInstance(context).cancelAllWorkByTag("Joke work");
    }
}
