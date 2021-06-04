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

            if (it.isNotEmpty()) {
                activityHomeBinding.tvTopArticle.visibility = View.VISIBLE
                activityHomeBinding.tvDescTopArticle.visibility = View.VISIBLE
            }
        })

        activityHomeBinding.apply {
            btnChat.setOnClickListener {
                startActivity(Intent(this@Home, ChatActivity::class.java))
            }

            btnCamera.setOnClickListener {
                startActivity(Intent(this@Home, Camera::class.java))
            }
        }
    }
}