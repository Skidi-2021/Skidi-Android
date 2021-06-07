package com.skidi.skidi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skidi.skidi.model.ArticlesItem
import com.skidi.skidi.model.NewsBackendInstance
import com.skidi.skidi.model.NewsResponse
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _news = MutableLiveData<NewsResponse>()
    private val _articles = MutableLiveData<List<ArticlesItem>>()
    val articles: LiveData<List<ArticlesItem>> = _articles

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            try {
                _news.value = NewsBackendInstance.newsInstance.getNews()
                _articles.value = _news?.value?.articles as List<ArticlesItem>
            } catch (e: Exception) {
                Log.e("news", e.message.toString())
            }
        }
    }
}