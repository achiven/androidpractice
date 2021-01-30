package com.example.badthreadexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

/*
when your app is destroyed, the app loses thread reference.
So, you cannot make a thread null.

Thus, mCount will reach 100.
 */

public class MainActivity extends AppCompatActivity {

    private Thread mThread;
    private int mCount = 0;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startThread(View view) {
        if(mThread == null){
            mThread = new Thread("My Thread"){
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        mCount++;
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            break;
                        }
                        Log.d(TAG, "thread is running " + mCount);

                    }
                }
            };

            mThread.start();
        }
    }

    public void stopThread(View view) {
        if(mThread != null) {
            mThread.interrupt();
            mThread = null;
            mCount = 0;
        }

    }
}