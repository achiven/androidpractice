package com.example.coverbackgroundsolution;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CORES);        // background thread에서 동작할 수 있도록 executor을 만들어 주는 것
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
}
