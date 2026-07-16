package com.AppFlix.i220968_i228810.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val username: String,
    val userProfileImageUrl: String,
    val mediaUrl: String,
    val mediaType: String,
    val createdAt: Long,
    val expiresAt: Long
)