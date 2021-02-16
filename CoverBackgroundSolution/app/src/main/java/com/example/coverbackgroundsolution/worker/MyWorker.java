package com.example.coverbackgroundsolution.worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.coverbackgroundsolution.R;

public class MyWorker extends Worker {
    private static final String CHANNEL_ID = "workermanager_channel";
    private NotificationManager notificationManager;
    private final int NOTI_ID = 3;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WorkManager long task")
                .setProgress(100, 0, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();


        ForegroundInfo info = new ForegroundInfo(NOTI_ID, notification);
        setForegroundAsync(info);       // It makes lasts longer than 1 minute.

        try {
            // background
            int num = 0;
            for (int i = 0; i < 100; i++) {
                num++;

                Data data = new Data.Builder()
                        .putInt("progress", num)
                        .build();
                setProgressAsync(data);

                showNotification(num);
                Thread.sleep(100);
            }

        } catch (Exception e) {
            return Result.failure();
        }

        return Result.success();
    }


    // IntentService will do something independently without view(Activity, fragment)
    // so... to know what's going on it, I'll make notification for that.
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Worker";
            String description = "Worker is running";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(int progress) {
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WorkManager long task")
                .setProgress(100, progress, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(NOTI_ID, notification);

    }
}
