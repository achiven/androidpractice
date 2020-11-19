package com.example.mvpbase.di

import com.example.mvpbase.constant.Constant
import com.example.mvpbase.model.GithubApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideGithubApi(
        okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        factory: Converter.Factory
    ): GithubApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(callAdapter)
            .addConverterFactory(factory)
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        var builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        return builder.build()
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.createAsync()
    }

    @Provides
    @Singleton
    fun provideConvertFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

}