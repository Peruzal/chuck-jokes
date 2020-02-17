package com.mambure.chuckjokes;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class JokeNotification {
    public static final String CHANNEL_ID = BuildConfig.APPLICATION_ID;

    public static void notify(Context context, String joke) {
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setSummaryText(joke);
        style.bigText(joke);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_sentiment_very_satisfied_black_24dp);
        builder.setContentTitle("New Joke");
        builder.setStyle(style);
        notify(context, builder.build());
    }

    private static void notify(Context context, final Notification notification) {
        NotificationManagerCompat.from(context).notify(304, notification);
    }

    public static void cancel(Context context) {
        NotificationManagerCompat.from(context).cancel(304);
    }


}
