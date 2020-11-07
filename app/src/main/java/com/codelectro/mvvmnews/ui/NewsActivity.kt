package com.codelectro.mvvmnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.codelectro.mvvmnews.R
import com.codelectro.mvvmnews.api.NetworkConnectionInterceptor
import com.codelectro.mvvmnews.api.NewsApi
import com.codelectro.mvvmnews.db.ArticleDatabase
import com.codelectro.mvvmnews.repositories.NewsRepository
import com.codelectro.mvvmnews.ui.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val newApi = NewsApi(networkConnectionInterceptor)
        val repository = NewsRepository(ArticleDatabase(this), newApi)
        val viewModelProviderFactory = NewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }
}