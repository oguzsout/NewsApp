package com.oguzdogdu.newsapp.repo

import com.oguzdogdu.newsapp.service.NewsInterface
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsInterface: NewsInterface) {
    suspend fun getNews(
        country: String,
        category: String,
    ) = newsInterface.getAllNews(country, category)

}