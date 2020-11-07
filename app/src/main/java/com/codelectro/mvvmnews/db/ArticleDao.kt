package com.codelectro.mvvmnews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codelectro.mvvmnews.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM articles WHERE title=:title")
    fun getArticleByTitle(title: String): LiveData<Article>
}