package com.skidi.skidi.viewmodel

import androidx.lifecycle.ViewModel
import com.skidi.skidi.model.ChatDummy
import com.skidi.skidi.model.ChatEntity

class GenerateDummyViewModel: ViewModel() {
    fun getDummyChat(): List<ChatEntity> = ChatDummy.generateDummyChat()
}