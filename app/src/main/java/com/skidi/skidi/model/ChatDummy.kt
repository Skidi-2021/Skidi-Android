package com.skidi.skidi.model

object ChatDummy {
    fun generateDummyChat(): List<ChatEntity> {
        val chat = ArrayList<ChatEntity>()

        chat.add(
            ChatEntity(
                1,
                "bot",
                "Hi, can I help you?",
                "Chat",
                "18.50",
                ""
            )
        )

        chat.add(
            ChatEntity(
                2,
                "user",
                "I have a skin problem",
                "Chat",
                "18.51",
                ""
            )
        )

        chat.add(
            ChatEntity(
                3,
                "bot",
                "What's symtomps that you have?",
                "Chat",
                "18.52",
                ""
            )
        )

        chat.add(
            ChatEntity(
                4,
                "user",
                "I have red spotting on my hand",
                "Chat",
                "18.53",
                ""
            )
        )
        return chat
    }
}