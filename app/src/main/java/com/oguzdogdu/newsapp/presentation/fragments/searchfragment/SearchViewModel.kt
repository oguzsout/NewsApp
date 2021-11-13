package com.oguzdogdu.newsapp.presentation.fragments.searchfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzdogdu.newsapp.data.repository.NewsRepositoryImpl
import com.oguzdogdu.newsapp.domain.model.NewsResponse
import com.oguzdogdu.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepositoryImpl) :
    ViewModel() {

    private val _response = MutableLiveData<Resource<NewsResponse>>()
    val newsResponse: LiveData<Resource<NewsResponse>>
        get() = _response


     fun searchNews(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.searchNews(searchQuery)
            _response.postValue(result)
        }
    }

}