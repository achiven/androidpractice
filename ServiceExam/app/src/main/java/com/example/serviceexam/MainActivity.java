package com.example.serviceexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private MyIntentService myIntentService = new MyIntentService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onStartThread(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);

    }

    public void onStopThread(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    public void onStartIntentService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction("startForeground");
        startService(intent);
    }

    public void onStopIntentService(View view) {
        stopService(new Intent(getApplicationContext(), MyIntentService.class));
    }

    public void onStartForeService(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.setAction("startForeground");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent);
        }else
            startService(intent);
    }

    public void onStopForeService(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.setAction("stopForeground");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent);
        }else
            startService(intent);
    }
}