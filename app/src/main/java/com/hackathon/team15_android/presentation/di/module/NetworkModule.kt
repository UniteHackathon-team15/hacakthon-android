package com.hackathon.team15_android.presentation.di.module

import android.util.Log
import com.hackathon.team15_android.BuildConfig
import com.hackathon.team15_android.data.remote.api.AiAPI
import com.hackathon.team15_android.data.remote.api.PostAPI
import com.hackathon.team15_android.presentation.di.AiOkthttp
import com.hackathon.team15_android.presentation.di.AiRetrofit
import com.hackathon.team15_android.presentation.di.BaseOkthttp
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

    @BaseOkthttp
    @Provides
    @Singleton
    fun provideBaseOkhttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @AiOkthttp
    @Provides
    @Singleton
    fun provideAIOkhttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20000, TimeUnit.SECONDS)
            .readTimeout(20000, TimeUnit.SECONDS)
            .writeTimeout(20000, TimeUnit.SECONDS)
            .build()
    }

    @BaseRetrofit
    @Provides
    @Singleton
    fun provideBaseRetrofitInstance(
        @BaseOkthttp okHttpClient: OkHttpClient,
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
        @AiOkthttp okHttpClient: OkHttpClient,
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

    @Provides
    @Singleton
    fun provideAiAPI(@AiRetrofit retrofit: Retrofit): AiAPI = retrofit.create(AiAPI::class.java)
}
