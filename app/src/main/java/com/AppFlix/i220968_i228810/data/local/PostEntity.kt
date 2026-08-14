package com.AppFlix.i220968_i228810.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userProfileImageUrl: String,
    val mediaUrl: String,
    val caption: String,
    val likesCount: Int,
    val commentsCount: Int,
    val createdAt: Long,
    val isLikedByCurrentUser: Boolean
)