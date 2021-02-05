package com.example.serviceexam;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/*
    Thread를 별도로 생성하지 않아도 Thread로 동작함
    여러 번 호출 되어도 순서대로 수행 된다.
        스스로 onDestory가 호출 된다.
 */

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        for (int i = 0; i < 5; i++) {
            Log.d(TAG, "IntentService is running " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // IntentService를 여러번 호출 하면 순서대로 수행 되는데,
    // 마지막 호출이 끝나야지 onDestory()가 호출된다.
    // 하지만  "stopService(new Intent(getApplicationContext(), MyIntentService.class));" 같이
    //      강제로 stopService()를 호출 하면, onDestroy()가 호출 되지만, 수행 하고 있던 녀석을 마저 끝내고 Service가 종료 된다.
    // IntentService는 의도 자체가 Activity가 종료 되더라도 독립적으로 일을 마치도록 만들어졌기 때문에,
    //     강제로 stop 하고 싶으면 Service class를 사용해야 하는게 맞다.
    @Override
    public void onDestroy() {
        super.onDestroy();

        stopSelf();
        System.out.println("MyIntentService destroy()");
    }
}