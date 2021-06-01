package com.skidi.skidi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.skidi.skidi.databinding.ActivityImageBinding

class Image : AppCompatActivity() {
    private lateinit var activityImageBinding: ActivityImageBinding

    companion object{
       const val EXTRA_IMAGE_URL = "extra_image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityImageBinding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(activityImageBinding.root)

        val savedUri = intent.getStringExtra(EXTRA_IMAGE_URL)
//        Log.d("imageeee", savedUri.toString())
        val msg = "Photo capture succeeded: $savedUri"

        Glide
            .with(this)
            .load(savedUri)
            .into(activityImageBinding.ivImage)
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
    }
}