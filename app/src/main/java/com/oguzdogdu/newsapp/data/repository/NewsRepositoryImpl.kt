package com.oguzdogdu.newsapp.data.repository

import com.oguzdogdu.newsapp.data.remote.NewsInterface
import com.oguzdogdu.newsapp.domain.model.NewsResponse
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
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }


    override suspend fun searchNews(searchQuery: String): Resource<NewsResponse> {
        return try {
            val response = newsInterface.searchForNews(searchQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }
}