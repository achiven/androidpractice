package com.example.serviceexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private MyService myService;
    private boolean isBound;

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

        if(isBound){
            myService.stopService();
        }
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

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);           // 서비스를 자동으로 생성해주고 bind 까지 해주는 flag
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(isBound){            // 앱이 동작할 때만 BindService가 유지되는 방식
            unbindService(mConnection);
            isBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder)service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 예상치 못한 종료 // unexpected terminal case

        }
    };

    public void getCountValue(View view) {
        if(isBound){
            Toast.makeText(this, "Count : " + myService.getCount(), Toast.LENGTH_SHORT).show();
        }
    }
}