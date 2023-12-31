package com.hackathon.team15_android.presentation.di.module

import android.util.Log
import com.hackathon.team15_android.BuildConfig
import com.hackathon.team15_android.data.remote.api.PostAPI
import com.hackathon.team15_android.presentation.di.AiRetrofit
import com.hackathon.team15_android.presentation.di.BaseRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.logging.HttpLoggingInterceptor
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.v("HTTP", message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // 요청을 시작한 후 서버와의 TCP handshake 가 완료되기까지 지속되는 시간
            .connectTimeout(30, TimeUnit.SECONDS)
            // 모든 바이트가 전송되는 속도륵 감시
            .readTimeout(30, TimeUnit.SECONDS)
            // 읽기 타임 아웃의 반대 방향. 얼마나 빨리 서버에 바이트를 보낼 수 있는지 확인
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @BaseRetrofit
    @Provides
    @Singleton
    fun provideBaseRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @AiRetrofit
    @Provides
    @Singleton
    fun provideAiRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AI_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providePostAPI(@BaseRetrofit retrofit: Retrofit): PostAPI = retrofit.create(PostAPI::class.java)
}
