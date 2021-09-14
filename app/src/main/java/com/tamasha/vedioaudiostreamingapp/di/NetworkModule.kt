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
    fun providesContext(application: Application): Context = application.applicationContext

    /**
     * Retrofit
     * */
/*

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, hostManager: APIHostManager): Retrofit =
        RetrofitBuilderFactory.createRetrofit(okHttpClient, hostManager)
*/

    /**
     * Api services
     * */

    @Singleton
    @Provides
    fun provideRetrofit(application: Application): ApiService =
        RetrofitBuilder.getRetrofit(application)

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java
    )

    @Singleton
    @Provides
    fun providesSharedPreference(
        context: Context
    ): SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)


    /*  @Singleton
      @Provides
      fun provideAuthAPIService(retrofit: Retrofit): ApiAuthenticationServices =
          retrofit.create(ApiAuthenticationServices::class.java)
  */
    /**
     * OkHttpClient
     * */

    /*  @Provides
      @Singleton
      internal fun provideOkHttpClient(
          networkManager: NetworkManager
      ): OkHttpClient = networkManager.getClient()
  */

    /* @Singleton
     @Provides
     fun providesAPIHostManager(
         preferences: SharedPreferences
     ): APIHostManager = APIHostManager(preferences = preferences)*/
}
