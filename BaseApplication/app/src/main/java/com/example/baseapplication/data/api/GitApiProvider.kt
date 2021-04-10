package com.example.baseapplication.data.api

import com.example.baseapplication.data.api.GitApis.Companion.GIT_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GitApiProvider {
    fun provideRepoApi() = getRetrofitBuild().create(GitApis::class.java)

    private fun getRetrofitBuild() = Retrofit.Builder()
        .baseUrl(GIT_BASE_URL)
        .client(getOkhttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(getGsonConverter())
        .build()

    private fun getGsonConverter() = GsonConverterFactory.create()

    private fun getOkhttpClient() = OkHttpClient.Builder()
        .apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
            addInterceptor(getLoggingInterceptor())
        }.build()

    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }
}