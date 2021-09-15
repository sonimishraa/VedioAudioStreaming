package com.tamasha.vedioaudiostreamingapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.tamasha.vedioaudiostreamingapp.BuildConfig
import com.tamasha.vedioaudiostreamingapp.network.ApiService
import com.tamasha.vedioaudiostreamingapp.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(application: Application): ApiService  =
        RetrofitBuilder.getRetrofit(application)

}
