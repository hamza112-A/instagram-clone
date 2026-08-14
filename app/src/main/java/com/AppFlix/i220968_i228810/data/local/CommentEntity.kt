package com.AppFlix.i220968_i228810.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val userId: String,
    val username: String,
    val userProfileImageUrl: String,
    val text: String,
    val createdAt: Long
)