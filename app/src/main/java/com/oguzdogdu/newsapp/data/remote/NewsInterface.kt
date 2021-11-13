package com.oguzdogdu.newsapp.data.remote

import com.oguzdogdu.newsapp.util.Constants.API_KEY
import com.oguzdogdu.newsapp.domain.model.NewsResponse
import com.oguzdogdu.newsapp.util.Constants.SORT_BY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("v2/top-headlines")
    suspend fun getAllNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = 50,
        @Query("sortBy") sortBy: String = SORT_BY,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("pageSize") pageSize: Int = 25,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}