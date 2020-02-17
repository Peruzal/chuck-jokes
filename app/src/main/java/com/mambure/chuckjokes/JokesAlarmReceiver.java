package com.mambure.chuckjokes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JokesAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> stringFuture = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return HttpRequestUtil.makeRequest(MainActivity.JOKES_SERVER_URL);
            }
        });

        try {
            JokeNotification.notify(context, stringFuture.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
