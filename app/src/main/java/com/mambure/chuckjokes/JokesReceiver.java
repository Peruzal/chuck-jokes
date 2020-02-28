package com.mambure.chuckjokes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.mambure.chuckjokes.data.Joke;
import com.mambure.chuckjokes.data.RemoteJokeRepository;

import java.util.concurrent.CountDownLatch;

public class JokesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction() == "JOKE NOTIFICATION") {
//           RemoteJokeRepository jokeRepository = new RemoteJokeRepository(context);
//
//           final CountDownLatch latch = new CountDownLatch(1);
//
//           LiveData<Joke> jokeLiveData = jokeRepository.getDataStream();
//            jokeLiveData.observeForever(new Observer<Joke>() {
//                @Override
//                public void onChanged(Joke joke) { ;
//                    latch.countDown();
//                    jokeLiveData.removeObserver(this);
//
//                }
//            });
//            jokeRepository.getJoke(MainActivity.JOKES_SERVER_URL);
//
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        JokeNotification.notify(context, "Notification from receiver");
    }
}
