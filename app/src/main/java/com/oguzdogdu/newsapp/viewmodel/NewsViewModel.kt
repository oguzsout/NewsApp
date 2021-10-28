package com.oguzdogdu.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzdogdu.newsapp.model.NewsResponse
import com.oguzdogdu.newsapp.repo.NewsRepository
import com.oguzdogdu.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository):ViewModel()  {

   private val _response =  MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _response


    private fun getNews( category: String,
                         country: String) = viewModelScope.launch {
        repository.getAllNews(category,country).let { response ->
            if (response.status.equals(Resource.success(response))){
                _response.postValue(response)
            } else {
                Resource.error("No data",response)
            }
        }
    }

}