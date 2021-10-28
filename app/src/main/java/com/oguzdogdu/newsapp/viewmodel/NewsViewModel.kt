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
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _response = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _response


    init {
        getBreakingNews()
    }

    private fun getBreakingNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = repository.getNews("tr", "business", 1)
                _response.postValue(Resource.Success(client.body()).data)
            } catch (e: Exception) {

            }
        }
    }
}


