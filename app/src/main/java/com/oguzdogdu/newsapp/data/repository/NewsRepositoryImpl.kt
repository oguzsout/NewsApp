package com.oguzdogdu.newsapp.data.repository

import com.oguzdogdu.newsapp.domain.model.NewsResponse
import com.oguzdogdu.newsapp.data.remote.NewsInterface
import com.oguzdogdu.newsapp.domain.repository.NewsRepository
import com.oguzdogdu.newsapp.util.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsInterface: NewsInterface) :
    NewsRepository {
    override suspend fun getNews(country: String, category: String): Resource<NewsResponse> {
        return try {
            val response = newsInterface.getAllNews(country, category)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Error", null)
            } else {
                Resource.Error("Error", null)
            }
        } catch (e: Exception) {
            Resource.Error("No data!", null)
        }
    }
}