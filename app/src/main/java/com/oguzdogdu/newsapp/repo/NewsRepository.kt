package com.oguzdogdu.newsapp.repo

import com.oguzdogdu.newsapp.di.Constants.API_KEY
import com.oguzdogdu.newsapp.service.NewsInterface
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsInterface: NewsInterface) {
    suspend fun getAllNews(
        category: String,
        country: String
    ) = newsInterface.getAllNews(country, category, API_KEY)

}