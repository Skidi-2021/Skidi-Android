package com.skidi.skidi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skidi.skidi.R
import com.skidi.skidi.databinding.ActivityChatBinding
import com.skidi.skidi.model.ChatEntity
import com.skidi.skidi.viewmodel.GenerateDummyViewModel
import com.skidi.skidi.viewmodel.GetResponseViewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var adapter: ChatAdapter
    private lateinit var binding: ActivityChatBinding

    companion object {
        const val EXTRA_SYMPTOM = "extra_symptom"
        const val EXTRA_LAT = "extra_lat"
        const val EXTRA_LONG = "extra_long"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatAdapter(this)
        adapter.notifyDataSetChanged()

        val symptom = intent.getStringExtra(EXTRA_SYMPTOM)
        val lat = intent.getStringExtra(EXTRA_LAT)
        val long = intent.getStringExtra(EXTRA_LONG)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[GetResponseViewModel::class.java]

        if (symptom != null) {
            if (lat != null) {
                if (long != null) {
                    viewModel.postSymptom(symptom, lat.toDouble(), long.toDouble())
                }
            }
        }

        viewModel.getResponse().observe(this@ChatActivity, {
//            runOnUiThread {
            Log.d("symptom", it.message.toString())
            Log.d("symptom", "get response is running!")
            adapter.setMessage(it)
            binding.apply {
                rvChat.layoutManager = LinearLayoutManager(this@ChatActivity)
                rvChat.setHasFixedSize(false)
                rvChat.adapter = adapter
                rvChat.scrollToPosition(adapter.itemCount - 1)
            }
//            }
        })
    }
}