package com.example.coverbackgroundsolution.repository;

import android.os.Handler;

import com.example.coverbackgroundsolution.Result;

import java.util.concurrent.Executor;

public class NumberRepository {
    private final Executor executor;
    private final Handler resultHandler;

    public NumberRepository(Executor executor, Handler resultHandler) {
        this.executor = executor;
        this.resultHandler = resultHandler;
    }


    // callback 관련
    // 이것을 activity에서 접근하려면 이것을 instance가 있어야 하지 않나?? achiven
    // or NumberRepository.longTask(callback) 을 넣어서 결과를 받는 것이다.
    //          이렇게 되면 activity안의 callback instance의 onComplete() 함수 안에서 Result 결과를 처리 할 수 있다.
    public void longTask(final RepositoryCallback<Integer> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    // background
                    int num = 0;
                    for (int i = 0; i < 100; i++) {
                        num++;

                        // UI update here by callback
                        Result<Integer> result = new Result.Success<>(num);

//                    callback.onComplete(result);          // !! background thread에서 UI thread의 값을 직접 바꾸려고 하면 ANR 발생!!
                        notifyResult(result, callback);         // 대신해서 이렇게 처리 해야 함

                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    Result<Integer> result = new Result.Error<>(e);
                    notifyResult(result, callback);
                }
            }
        });

    }

    // background thread가 UI로 데이터를 문제 없이 보낸다.
    // handler안에서 Runnable을 만들어서 데이터를 보내면 문제가 없어진다.
    private void notifyResult(
            final Result<Integer> result,
            final RepositoryCallback<Integer> callback) {
        resultHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onComplete(result);
            }
        });
    }
}
