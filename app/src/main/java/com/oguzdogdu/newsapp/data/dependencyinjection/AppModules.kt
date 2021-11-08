package com.oguzdogdu.newsapp.data.dependencyinjection

import com.oguzdogdu.newsapp.util.Constants.BASE_URL
import com.oguzdogdu.newsapp.domain.repository.NewsRepository
import com.oguzdogdu.newsapp.data.repository.NewsRepositoryImpl
import com.oguzdogdu.newsapp.data.remote.NewsInterface
import com.oguzdogdu.newsapp.presentation.fragments.newsfragment.adapter.NewsAdapter
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

    @Singleton
    @Provides
    fun provideRepository(newsInterface: NewsInterface): NewsRepository {
        return NewsRepositoryImpl(newsInterface)
    }

    @Singleton
    @Provides
    fun newsAdapter(): NewsAdapter = NewsAdapter()

}