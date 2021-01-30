package com.example.serviceexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

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
        startService(intent);
    }
}