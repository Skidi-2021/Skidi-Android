package com.skidi.skidi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.skidi.skidi.R

class ReadNews : AppCompatActivity(R.layout.read_news_activity) {
    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_CONTENT = "extra_content"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            var extras = intent.extras
            var title = extras?.getString(EXTRA_TITLE)
            var date = extras?.getString(EXTRA_DATE)
            var content = extras?.getString(EXTRA_CONTENT)
            var image = extras?.getString(EXTRA_IMAGE)
            var url = extras?.getString(EXTRA_URL)
            var bundle = bundleOf(
                "title" to title,
                "date" to date,
                "content" to content,
                "image" to image,
                "url" to url
            )
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ReadNewsFragment>(R.id.fragment_container_view, args = bundle)
            }
        }
    }
}