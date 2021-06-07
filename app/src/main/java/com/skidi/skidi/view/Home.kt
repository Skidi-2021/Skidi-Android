package com.skidi.skidi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skidi.skidi.R
import com.skidi.skidi.databinding.ActivityHomeBinding
import com.skidi.skidi.model.ArticlesItem
import com.skidi.skidi.viewmodel.GetResponseViewModel
import com.skidi.skidi.viewmodel.NewsViewModel

class Home : AppCompatActivity() {
    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[NewsViewModel::class.java]

        adapter = NewsAdapter()

        activityHomeBinding.apply {
            rvNews.layoutManager = LinearLayoutManager(applicationContext)
            rvNews.setHasFixedSize(true)
            rvNews.adapter = adapter
        }

        viewModel.articles.observe(this, {
            adapter.setNews(it)

            adapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
                override fun onItemClickCallback(data: ArticlesItem) {
                    super.onItemClickCallback(data)
                    var readIntent = Intent(this@Home, ReadNews::class.java)
                    readIntent.putExtra(ReadNews.EXTRA_TITLE, data.title)
                    readIntent.putExtra(ReadNews.EXTRA_CONTENT, data.content)
                    readIntent.putExtra(ReadNews.EXTRA_IMAGE, data.urlToImage)
                    readIntent.putExtra(ReadNews.EXTRA_DATE, data.publishedAt)
                    readIntent.putExtra(ReadNews.EXTRA_URL, data.url)
                    startActivity(readIntent)
                }
            })

            if (it.isNotEmpty()) {
                activityHomeBinding.tvTopArticle.visibility = View.VISIBLE
                activityHomeBinding.tvDescTopArticle.visibility = View.VISIBLE
            }
        })

        activityHomeBinding.apply {
            btnChat.setOnClickListener {
                startActivity(Intent(this@Home, Camera::class.java))
            }

            btnCamera.setOnClickListener {
                startActivity(Intent(this@Home, Camera::class.java))
            }
        }
    }
}