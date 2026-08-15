package com.AppFlix.i220968_i228810.model

import com.google.firebase.database.Exclude

data class Chat(
    val chatId: String = "",
    val userIds: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastMessageId: String = "",
    val lastMessageTime: Long = 0L,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    
    // Populated locally for display (not stored in Firebase)
    @get:Exclude
    @set:Exclude
    var otherUserId: String = "",
    @get:Exclude
    @set:Exclude
    var otherUserName: String = "",
    @get:Exclude
    @set:Exclude
    var otherUserProfileImage: String = ""
)