package com.tamasha.vedioaudiostreamingapp.network

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tamasha.vedioaudiostreamingapp.model.ProjectConstants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    fun getRetrofit(application: Application): ApiService {
        val client =
            OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ChuckerInterceptor(application)).addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also { reqBuilder ->
                        reqBuilder.addHeader("Authorization", "")
                            .header("Content-Type", "application/json")
                    }.build())
                }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)
        return apiService
    }

}
