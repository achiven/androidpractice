package com.example.coverbackgroundsolution.repository;


import com.example.coverbackgroundsolution.Result;

// 이것을 activity에서 접근하려면 이것을 instance가 있어야 하지 않나?? achiven
// or NumberRepository.longTask(callback) 을 넣어서 결과를 받는 것이다.
//          이렇게 되면 activity안의 callback instance의 onComplete() 함수 안에서 Result 결과를 처리 할 수 있다.
public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}