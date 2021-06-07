package com.skidi.skidi.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClickCallback(data: ArticlesItem) {}
    }

    class NewsViewModel(val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.urlToImage)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
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

        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClickCallback(newsList[holder.absoluteAdapterPosition]) }
    }

    override fun getItemCount(): Int = newsList.size

}