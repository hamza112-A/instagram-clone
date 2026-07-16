package com.AppFlix.i220968_i228810.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0,
    val serverId: Int? = null, // Null if not yet synced
    val chatId: String, // We will treat "user1_user2" as local chat ID for simplicity
    val senderId: String,
    val receiverId: String,
    val type: String, // TEXT, IMAGE
    val text: String,
    val mediaUrl: String,
    val postId: String,
    val createdAt: Long,
    val isDeleted: Boolean = false,
    val syncStatus: Int = 0 // 0=Synced, 1=Pending Upload, 2=Pending Edit
)