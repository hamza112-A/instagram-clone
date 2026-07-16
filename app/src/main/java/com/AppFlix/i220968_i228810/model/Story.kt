package com.AppFlix.i220968_i228810.model

data class Story(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val userProfileImageUrl: String = "",
    val mediaUrl: String = "",
    val mediaType: String = "image",
    val createdAt: Long = 0L,
    val expiresAt: Long = 0L
)
