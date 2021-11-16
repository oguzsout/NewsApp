package com.oguzdogdu.newsapp.repo

import androidx.lifecycle.MutableLiveData
import com.oguzdogdu.newsapp.domain.model.Article
import com.oguzdogdu.newsapp.domain.model.NewsResponse
import com.oguzdogdu.newsapp.domain.repository.NewsRepository
import com.oguzdogdu.newsapp.util.Resource

class FakeNewsRepository : NewsRepository {

    private val article = mutableListOf<Article>()
    private val articleLiveData = MutableLiveData<List<Article>>(article)


    override suspend fun getNews(country: String, category: String): Resource<NewsResponse> {
        return Resource.success(NewsResponse(listOf(), 0))
    }

    override suspend fun searchNews(searchQuery: String): Resource<NewsResponse> {
        return Resource.success(NewsResponse(listOf(), 0))
    }
}