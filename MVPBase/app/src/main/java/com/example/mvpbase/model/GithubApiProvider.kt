package com.example.mvpbase.model

import com.example.mvpbase.constant.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiProvider {
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }


    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        var builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        return builder.build()
    }


    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

//
//    private fun provideOkHttpClient(interceptor: Interceptor) : OkHttpClient{
//        var builder = OkHttpClient.Builder()
//        builder.addInterceptor(interceptor)
//
//        return builder.build()
//    }
//
//    private fun provideLoggingInterceptor(): Interceptor{
//
//        return object : Interceptor{
//            val TAG = javaClass.simpleName
//            override fun intercept(chain: Interceptor.Chain): Response {
//                var request = chain.request()
//                Log.d(TAG, "request.method" + request.method())
//
//                var response = chain.proceed(request)
//                Log.d(TAG, "response.url" + response.request().url())
//
//                return response
//            }
//        }
//    }

}


