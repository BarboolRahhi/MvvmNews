package com.codelectro.mvvmnews.models

import com.codelectro.mvvmnews.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)