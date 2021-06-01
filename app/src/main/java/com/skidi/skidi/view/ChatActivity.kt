package com.skidi.skidi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skidi.skidi.R
import com.skidi.skidi.databinding.ActivityChatBinding
import com.skidi.skidi.model.ChatEntity
import com.skidi.skidi.viewmodel.GenerateDummyViewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var adapter: ChatAdapter
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatAdapter(this)
        adapter.notifyDataSetChanged()

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[GenerateDummyViewModel::class.java]

        val message = viewModel.getDummyChat()

        binding.apply {
            rvChat.layoutManager = LinearLayoutManager(this@ChatActivity)
            rvChat.setHasFixedSize(false)
            rvChat.adapter = adapter
            val chatM = chatMessage.text

//            adapter.setMessage(
//                    ChatEntity(
//                            2,
//                            "bot",
//                            "Welcome to skidi bot, can I help you?",
//                            "Chat",
//                            "12.55"
//                    )
//            )

//            btnSendChat.setOnClickListener {
//                val chatMessage = ChatEntity(
//                        2,
//                        "User",
//                        "I have a skin problem",
//                        "Chat",
//                        "12.56"
//                )

            runOnUiThread {
                adapter.setMessage(message)
                rvChat.scrollToPosition(adapter.itemCount - 1)
            }
//            }

//            adapter.setMessage(message)
        }

    }
}