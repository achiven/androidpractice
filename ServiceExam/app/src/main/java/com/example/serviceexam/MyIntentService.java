package com.example.serviceexam;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Log.d(TAG, "IntentService is running " + i);
        }
    }

}