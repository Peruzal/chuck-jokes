package com.mambure.chuckjokes;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;

public class MainActivity extends AppCompatActivity {
    public static final String JOKES_SERVER_URL = "https://api.chucknorris.io/jokes/random/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void createNotificationChannel() {


    }

    private void getJoke() {

    }



    private void scheduleJoke() {

    }

    private void scheduleJokeWithWorkManager() {

    }

    @Override
    protected void onStart() {

    }
}
