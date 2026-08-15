package com.AppFlix.i220968_i228810.model

data class Post(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val userProfileImageUrl: String = "",
    val mediaUrl: String = "",
    val caption: String = "",
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: Long = 0L
)
