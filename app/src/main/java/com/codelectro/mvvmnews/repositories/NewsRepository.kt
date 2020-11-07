package com.codelectro.mvvmnews.repositories

import com.codelectro.mvvmnews.api.NewsApi
import com.codelectro.mvvmnews.db.ArticleDatabase
import com.codelectro.mvvmnews.models.Article

class NewsRepository(
    private val db: ArticleDatabase,
    private val api: NewsApi
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
       api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(query: String, pageNumber: Int) =
        api.searchForNews(query, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()
    fun getArticleByTitle(title: String) = db.getArticleDao().getArticleByTitle(title)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)


//    suspend fun insertToDb() {
//        val response = getBreakingNews("in", 1)
//
//        if (response.isSuccessful) {
//            response.body()?.articles?.let {
//                for (article in it) {
//                    upsert(article)
//                }
//            }
//        }
//    }
}