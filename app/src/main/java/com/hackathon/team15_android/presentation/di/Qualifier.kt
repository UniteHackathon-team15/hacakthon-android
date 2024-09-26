package com.hackathon.team15_android.presentation.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AiRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AiOkthttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseOkthttp