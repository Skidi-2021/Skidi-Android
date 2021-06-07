package com.skidi.skidi.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.skidi.skidi.R
import com.skidi.skidi.databinding.ReadNewsFragmentBinding
import com.skidi.skidi.model.ArticlesItem
import com.skidi.skidi.viewmodel.NewsViewModel
import java.text.SimpleDateFormat

class ReadNewsFragment : Fragment(R.layout.read_news_fragment) {
    private lateinit var readNewsFragmentBinding: ReadNewsFragmentBinding

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        readNewsFragmentBinding = ReadNewsFragmentBinding.inflate(inflater, container, false)
        return readNewsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[NewsViewModel::class.java]

        adapter = NewsAdapter()

        readNewsFragmentBinding.apply {
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.setHasFixedSize(true)
            rvNews.adapter = adapter

            tvTitleMain.text = requireArguments().getString("title")
            tvContentMain.text = requireArguments().getString("content")
            tvDateMain.text =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(requireArguments().getString("date"))
                    .toString()

            Glide.with(requireActivity())
                .load(requireArguments().getString("image"))
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(ivImageMain)

            btnReadMore.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(requireArguments().getString("url"))
                context?.startActivity(openURL)
            }

            viewModel.articles.observe(viewLifecycleOwner, {
                adapter.setNews(it)

                adapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
                    override fun onItemClickCallback(data: ArticlesItem) {
                        super.onItemClickCallback(data)
                        var readIntent = Intent(requireActivity(), ReadNews::class.java)
                        readIntent.putExtra(ReadNews.EXTRA_TITLE, data.title)
                        readIntent.putExtra(ReadNews.EXTRA_CONTENT, data.content)
                        readIntent.putExtra(ReadNews.EXTRA_IMAGE, data.urlToImage)
                        readIntent.putExtra(ReadNews.EXTRA_DATE, data.publishedAt)
                        readIntent.putExtra(ReadNews.EXTRA_URL, data.url)
                        startActivity(readIntent)
                    }
                })

                if (it !== null) {
                    tvTopArticle.visibility = View.VISIBLE
                    tvDescTopArticle.visibility = View.VISIBLE
                }
            })
        }
    }

}