package com.oguzdogdu.newsapp.di

import com.oguzdogdu.newsapp.di.Constants.BASE_URL
import com.oguzdogdu.newsapp.service.NewsInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModules {

    @Singleton
    @Provides
    fun provideRetrofit(): NewsInterface {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NewsInterface::class.java)

    }
}