package com.oguzdogdu.newsapp.presentation.fragments.newsfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzdogdu.newsapp.domain.model.NewsResponse
import com.oguzdogdu.newsapp.data.repository.NewsRepositoryImpl
import com.oguzdogdu.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepositoryImpl) : ViewModel() {

    private val _response = MutableLiveData<Resource<NewsResponse>>()
    val newsResponse: LiveData<Resource<NewsResponse>>
        get() = _response

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNews(country = "tr", category = "business")
            _response.postValue(result)
        }
    }
}





