package com.oguzdogdu.newsapp.presentation.fragments.sportsfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzdogdu.newsapp.domain.model.NewsResponse
import com.oguzdogdu.newsapp.domain.repository.NewsRepository
import com.oguzdogdu.newsapp.util.Constants.COUNTRY
import com.oguzdogdu.newsapp.util.Constants.SPORTS
import com.oguzdogdu.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _response = MutableLiveData<Resource<NewsResponse>>()
    val newsResponse: LiveData<Resource<NewsResponse>>
        get() = _response

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNews(COUNTRY, SPORTS)
            _response.postValue(result)
        }
    }
}