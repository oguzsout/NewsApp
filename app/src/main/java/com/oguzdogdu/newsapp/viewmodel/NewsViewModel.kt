package com.oguzdogdu.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzdogdu.newsapp.model.NewsResponse
import com.oguzdogdu.newsapp.repo.NewsRepository
import com.oguzdogdu.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _response = MutableLiveData<Resource<NewsResponse>>()
    val newsResponse: LiveData<Resource<NewsResponse>>
        get() = _response

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getNews("tr", "general")
            _response.postValue(handleBreakingNewsResponse(response))
        }
    }
}

private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
    if (response.isSuccessful) {
        response.body()?.let { resultResponse ->
            return Resource.Success(resultResponse)
        }
    }
    return Resource.Error(response.message())
}




