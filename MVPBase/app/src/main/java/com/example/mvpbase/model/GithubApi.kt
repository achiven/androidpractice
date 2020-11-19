package com.example.mvpbase.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface GithubApi {

    @GET
    fun getUserList(@Url url: String) : Observable<UserResponse>

}
