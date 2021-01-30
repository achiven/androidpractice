package com.example.serviceexam;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


// long running tasks or background tasks

/*
    서비스가 끝나더라도 onDestory()가 호출 되지 않아서,
        프로그래머가 onDestory()를 호출해줘야 한다.

    여러 번 호출해도 첫 호출만 반응하고 나머지는 무시된다.
        if(mThread == null)     // 코드로 인하여
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    private Thread mThread;
    private int mCount = 0;


    public MyService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mThread == null){                                // 여러 번 호출해도 첫 호출만 반응하고 나머지는 무시된다.
            mThread = new Thread("My Thread"){
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        mCount++;
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            break;
                        }
                        Log.d(TAG, "Service is running " + mCount);
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
        if(mThread != null) {
            mThread.interrupt();
            mThread = null;
            mCount = 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}