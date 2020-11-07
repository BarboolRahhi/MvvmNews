package com.codelectro.mvvmnews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.codelectro.mvvmnews.R
import com.codelectro.mvvmnews.models.Article
import com.codelectro.mvvmnews.ui.NewsActivity
import com.codelectro.mvvmnews.ui.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    val TAG = "ArticleFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        var article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        viewModel.getArticleByTitle(article.title).observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: $it")
            it?.let {
                article = Article(
                    id = it.id,
                    title = article.title,
                    description = article.description,
                    url = article.url,
                    urlToImage = article.urlToImage,
                    source = article.source,
                    author = article.author,
                    content = article.content,
                    publishedAt = article.publishedAt
                )
            }

        })

        fab.setOnClickListener {
            viewModel.savedArticle(article)
            Snackbar.make(view, "Article saved successfully!", Snackbar.LENGTH_SHORT).show()
        }
    }
}