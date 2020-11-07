package com.codelectro.mvvmnews.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelectro.mvvmnews.api.NoInternetException
import com.codelectro.mvvmnews.models.Article
import com.codelectro.mvvmnews.models.NewsResponse
import com.codelectro.mvvmnews.repositories.NewsRepository
import com.codelectro.mvvmnews.util.Resource
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    private val TAG = "NewsViewModel"

    init {
        getBreakingNews("in")
    }

    fun getBreakingNews(countryCode: String) =
        viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
            try {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } catch (e: NoInternetException) {
                breakingNews.postValue(Resource.Error(e.message!!))
            } catch (throwable: Throwable) {
                Log.e(TAG, "getBreakingNews: ${throwable.message}")
            }

        }


    fun getSearchNews(query: String) =
        viewModelScope.launch {
            searchNews.postValue(Resource.Loading())
            try {
                val response = newsRepository.searchNews(query, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } catch (e: NoInternetException) {
                Log.e(TAG, "getSearchNews: ${e.message}")
                searchNews.postValue(Resource.Error(e.message!!))
            } catch (throwable: Throwable) {
                Log.e(TAG, "getSearchNews: ${throwable.message}")
            }


        }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = it
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticle = it.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = it
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticle = it.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    fun savedArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun getArticleByTitle(title: String) = newsRepository.getArticleByTitle(title)

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}