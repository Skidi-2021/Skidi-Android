package com.skidi.skidi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skidi.skidi.R
import com.skidi.skidi.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var activityHomeBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        activityHomeBinding.apply {
            btnChat.setOnClickListener {
                startActivity(Intent(this@Home, ChatActivity::class.java))
            }
            btnCamera.setOnClickListener {
                startActivity(Intent(this@Home, CameraActivity::class.java))
            }
        }
    }
}