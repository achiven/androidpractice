package com.example.coverbackgroundsolution.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.coverbackgroundsolution.App;
import com.example.coverbackgroundsolution.R;
import com.example.coverbackgroundsolution.Result;
import com.example.coverbackgroundsolution.repository.NumberRepository;

public class ForegroundService extends Service {
    private final String CHANNEL_ID = "ForegroundService_channel";
    private final int NOTI_ID = 200;
    private NumberRepository repository;
    private NotificationManager notificationManager;
    public MutableLiveData<Integer> progressLiveData = new MutableLiveData<>(0);

    public ForegroundService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        repository = new NumberRepository(
                ((App) getApplication()).executorService,
                ((App) getApplication()).mainThreadHandler
        );

        notificationManager = ContextCompat.getSystemService(this, NotificationManager.class);
        createNotificationChannel();


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Foreground Service")
                .setProgress(100, 0, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        startForeground(NOTI_ID, notification);

        repository.longTask(result -> {
            if (result instanceof Result.Success) {
                progressLiveData.postValue(((Result.Success<Integer>) result).data);        // UI thread에 있는 데이터를 접근 하기에 postValue()를 써야 함
                showNotification(((Result.Success<Integer>) result).data);

                // should stop itself or to be stopped by the others.
                // otherwise, it won't stop
                if(((Result.Success<Integer>) result).data >= 100){
                    stopSelf();
                }

            } else if (result instanceof Result.Error) {
//                Toast.makeText(getApplication(), TAG + " Error! ", Toast.LENGTH_SHORT).show();
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void createNotificationChannel() {
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

    private void showNotification(int progress) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Foreground Service")
                .setProgress(100, progress, false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(NOTI_ID, notification);

    }
}