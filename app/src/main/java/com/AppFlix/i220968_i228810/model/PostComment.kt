package com.AppFlix.i220968_i228810.model

data class PostComment(
    val id: String = "",
    val postId: String = "",
    val userId: String = "",
    val username: String = "",
    val userProfileImageUrl: String = "",
    val text: String = "",
    val createdAt: Long = 0L
)
