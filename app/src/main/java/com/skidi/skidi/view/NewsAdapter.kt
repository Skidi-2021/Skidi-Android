package com.skidi.skidi.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skidi.skidi.databinding.CardNewsBinding
import com.skidi.skidi.model.ArticlesItem
import com.skidi.skidi.model.NewsResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewModel>() {
    private val newsList = ArrayList<ArticlesItem>()

    fun setNews(data: List<ArticlesItem>) {
        newsList.clear()
        newsList.addAll(data)
        notifyDataSetChanged()
    }

    class NewsViewModel(val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.urlToImage)
                    .into(ivImage)
                tvDate.text =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(data.publishedAt)
                        .toString()
                tvTitle.text = data.title
                tvContent.text = data.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewModel {
        val view = CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewModel(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewModel, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

}