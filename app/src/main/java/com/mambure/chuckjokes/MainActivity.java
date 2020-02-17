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
    private ProgressBar progressBar;
    private TextView jokeTextView;
    private GetJokeTask getJokeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeTextView = findViewById(R.id.txtJoke);
        progressBar = findViewById(R.id.progressBar);
        Button getJokeButton = findViewById(R.id.btnGetJoke);
        getJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeTextView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                getJoke();
            }
        });

        Button scheduleJokeButton = findViewById(R.id.btnSchedule);
        scheduleJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJokeWithWorkManager();
            }
        });

        getJoke();
        createNotificationChannel();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(JokeNotification.CHANNEL_ID, "Jokes", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Joke notifications");
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.createNotificationChannel(notificationChannel);
        }
    }

    private void getJoke() {
        getJokeTask = new GetJokeTask();
        getJokeTask.execute(null, null);
    }

    private class GetJokeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestUtil.makeRequest(JOKES_SERVER_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            jokeTextView.setVisibility(View.VISIBLE);
            jokeTextView.setText(s);
        }
    }

    private void scheduleJoke() {
        Intent intent = new Intent(this, JokesAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 23, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        AlarmManagerCompat.setExactAndAllowWhileIdle(
                am, AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, pendingIntent);
    }

    private void scheduleJokeWithWorkManager() {
            JokeService.enqeue(this);
    }

    @Override
    protected void onStart() {
        JokeNotification.cancel(this);
        JokeService.cancel(this);
        super.onStart();
    }
}
