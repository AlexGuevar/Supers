package com.killacorp.supers.di

import com.killacorp.supers.data.api.AuthService
import com.killacorp.supers.utils.Extras
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun getRetrofit() : AuthService {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val interceptor = httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(Extras.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(AuthService::class.java)
    }
}