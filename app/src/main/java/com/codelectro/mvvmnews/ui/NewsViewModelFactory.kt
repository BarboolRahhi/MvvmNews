package com.codelectro.mvvmnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codelectro.mvvmnews.repositories.NewsRepository
import com.codelectro.mvvmnews.ui.viewmodels.NewsViewModel

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val newsRepository: NewsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}