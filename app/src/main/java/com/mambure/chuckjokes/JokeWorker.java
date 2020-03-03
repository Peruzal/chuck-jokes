package com.mambure.chuckjokes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mambure.chuckjokes.data.LocalJokeRepository;

import java.util.concurrent.TimeUnit;

public class JokeWorker extends Worker {


    public JokeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void enqueueWork(Context context) {

        Constraints constraints = new Constraints.Builder().
                setRequiredNetworkType(NetworkType.UNMETERED).
                setRequiresBatteryNotLow(true).build();
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(JokeWorker.class, PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS, TimeUnit.MILLISECONDS).
                setInitialDelay(20000, TimeUnit.MILLISECONDS).setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);
    }

    @NonNull
    @Override
    public Result doWork() {
        String joke = HttpRequestUtil.makeRequest(MainActivity.JOKES_SERVER_URL);
        JokeNotification.notify(getApplicationContext(), joke);
        return Result.success();
    }


}
