package com.example.serviceexam;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;


// long running tasks or background tasks

/*
    서비스가 끝나더라도 onDestory()가 호출 되지 않아서,
        프로그래머가 onDestory()를 호출해줘야 한다.

    여러 번 호출해도 첫 호출만 반응하고 나머지는 무시된다.
        if(mThread == null)     // 코드로 인하여
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    private static final String CHANNEL_ID = "default";

    private Thread mThread;
    private int mCount = 0;

    // 이 변수와 관련된 코드는 무시 해도 됩니다.
    // 하지만 특수한 케이스에 한하여, 이런식으로 서비스를 제어 할 수도 있다는 것을 보여주기 위해서 작성했습니다.
    private boolean isReqStop = false;

    private final IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    private boolean isForegroundService() {
        NotificationManager manager = getSystemService(NotificationManager.class);
        for (StatusBarNotification notification : manager.getActiveNotifications()) {
            if (notification.getNotification().getChannelId().equals(CHANNEL_ID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if ("startForeground".equals(intent.getAction())) {
            startForegroundService();
        } else if ("stopForeground".equals(intent.getAction())) {
            if(isForegroundService()){
                stopForeground(true);
                stopSelf();
            }
        } else if (mThread == null) {                                // 여러 번 호출해도 첫 호출만 반응하고 나머지는 무시된다.
            mThread = new Thread("My Thread") {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        mCount++;
                        Log.d(TAG, "Service is running " + mCount);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }

                        if (isReqStop){
                            stopSelf();
                            onDestroy();
                            break;
                        }
                    }
                }
            };

            mThread.start();
        }

        // This Service will recreate without intent, even if the service is destroyed.
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy()");
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
            mCount = 0;
            isReqStop = false;
        }
    }

    public void stopService(){
        isReqStop = true;
        stopSelf();
        onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getCount(){
        return mCount;
    }


    private void startForegroundService() {

        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("inputExtra")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}