package com.seweryn.cvapp.di

import com.seweryn.cvapp.data.Constants
import com.seweryn.cvapp.data.remote.CVApi
import com.seweryn.cvapp.tools.network.NetworkConnectionInterceptor
import com.seweryn.cvapp.utils.network.ConnectionManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule  {

    @Provides
    @Singleton
    fun provideOkHttpClient(connectionManager: ConnectionManager): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(connectionManager))
            .build()

    @Provides
    @Singleton
    fun provideEventsApiClient(okHttpClient: OkHttpClient): Retrofit {
        return buildRetrofitClient(
            okHttpClient,
            Constants.CV_BASE_URL
        )
    }

    @Provides
    @Singleton
    fun provideEventsApiInterface(retrofit: Retrofit): CVApi {
        return retrofit.create(CVApi::class.java)
    }

    private fun buildRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}