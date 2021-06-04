package com.skidi.skidi.view

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skidi.skidi.R
import com.skidi.skidi.databinding.BotMessageBinding
import com.skidi.skidi.databinding.UserMessageBinding
import com.skidi.skidi.model.ChatEntity

private const val VIEW_TYPE_USER_MESSAGE = 1
private const val VIEW_TYPE_BOT_MESSAGE = 2

class ChatAdapter(val context: Context) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    //    Gonna use this later
    //    private var chat: ArrayList<ChatEntity> = ArrayList()
    private var chat = ArrayList<ChatEntity>()

    open class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(message: ChatEntity) {}
    }

    fun setMessage(message: ChatEntity) {
        //Change to "message: ChatEntity" later
        if (message == null) return
//        this.chat.clear()
        this.chat.add(message)
        notifyDataSetChanged()
    }

    inner class UserChatHolder(view: View) : ChatViewHolder(view) {
        val binding: UserMessageBinding = UserMessageBinding.bind(view)
        override fun bind(message: ChatEntity) {
            super.bind(message)
//            binding.tvUserMessage.text = message.message
            Glide.with(itemView)
                .load(message.img)
                .into(binding.imgSymptomPhoto)
            binding.tvTime.text = message.time
        }
    }

    inner class BotChatHolder(view: View) : ChatViewHolder(view) {
        val binding: BotMessageBinding = BotMessageBinding.bind(view)

        override fun bind(message: ChatEntity) {
            super.bind(message)
            binding.tvBotMessage.text = message.message
            binding.tvTime.text = message.time
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = chat.get(position)
        return if (message.sender == "bot") {
            VIEW_TYPE_BOT_MESSAGE
        } else {
            VIEW_TYPE_USER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return if (viewType == VIEW_TYPE_BOT_MESSAGE) {
//val botMessageBinding =BotMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BotChatHolder(LayoutInflater.from(context).inflate(R.layout.bot_message, parent, false))
        } else {
            UserChatHolder(
                LayoutInflater.from(context).inflate(R.layout.user_message, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = chat[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = chat.size
}