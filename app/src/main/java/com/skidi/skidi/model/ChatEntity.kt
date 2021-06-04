package com.skidi.skidi.model

data class ChatEntity(
        var id: Int = 0,
        var sender: String?,
        var message: String?,
        var type: String,
        var time: String,
        var img: String?,
        var link: String?
)
