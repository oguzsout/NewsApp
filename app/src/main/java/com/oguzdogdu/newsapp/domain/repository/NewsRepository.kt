package com.oguzdogdu.newsapp.domain.repository

import com.oguzdogdu.newsapp.domain.model.NewsResponse
import com.oguzdogdu.newsapp.util.Resource

interface NewsRepository {

    suspend fun getNews(country: String, category: String): Resource<NewsResponse>
}