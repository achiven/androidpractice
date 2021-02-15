package com.example.coverbackgroundsolution.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.coverbackgroundsolution.R;

public class JobService extends JobIntentService {

//    NotificationManager notificationManager = getSystemService(NotificationManager.class);                // this style is available since version 23.
//    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);     // old style but still working
//    private NotificationManager notificationManager = ContextCompat.getSystemService(this, NotificationManager.class);    // alternative
    private NotificationManager notificationManager;

    private final String CHANNEL_ID = "JobIntentService_channel";


    public static void enqueueWork(@NonNull Context context, @NonNull Intent intent){
        JobIntentService.enqueueWork(context, JobService.class, 1000, intent);

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        notificationManager = ContextCompat.getSystemService(this, NotificationManager.class);    // alternative

        createNotificationChannel();
        try {
            // background
            int num = 0;
            for (int i = 0; i < 100; i++) {
                num++;

                showNotification(num);
                Thread.sleep(100);
            }

        }catch (Exception e){

        }
    }

    // IntentService will do something independently without view(Activity, fragment)
    // so... to know what's going on it, I'll make notification for that.
    private void createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "JonIntentService";
            String description = "JonIntentService is running";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(int progress){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Job Service")
                .setProgress(100, progress, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(2, notification);

    }
}
