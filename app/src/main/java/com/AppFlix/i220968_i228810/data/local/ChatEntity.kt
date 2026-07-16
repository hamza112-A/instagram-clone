package com.AppFlix.i220968_i228810.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val chatId: String, // "user1_user2"
    val serverChatId: Int,
    val otherUserId: String,
    val otherUserName: String,
    val otherProfileImage: String,
    val lastMessage: String,
    val lastMessageTime: Long
)