package com.AppFlix.i220968_i228810.model

enum class MessageType { TEXT, IMAGE, POST_SHARE, VIDEO, FILE } // Added VIDEO and FILE

data class Message(
    val messageId: String = "",
    val chatId: String = "",
    val senderId: String = "",
    val senderUsername: String = "",
    val type: MessageType = MessageType.TEXT,
    val text: String = "",
    val mediaUrl: String = "",
    val postId: String = "",
    val sentAt: Long = 0L,
    val editedAt: Long? = null,
    val deleted: Boolean = false
)