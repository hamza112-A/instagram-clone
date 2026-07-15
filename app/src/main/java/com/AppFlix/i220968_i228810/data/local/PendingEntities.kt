package com.AppFlix.i220968_i228810.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Queue for Posts waiting to upload
@Entity(tableName = "pending_posts")
data class PendingPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val caption: String,
    val mediaUri: String, // Local file path
    val createdAt: Long
)

// Queue for Stories waiting to upload
@Entity(tableName = "pending_stories")
data class PendingStoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mediaUri: String,
    val mediaType: String, // "image" or "video"
    val createdAt: Long
)

// --- NEW ENTITIES FOR LIKES AND COMMENTS ---

// Queue for Likes waiting to sync
@Entity(tableName = "pending_likes")
data class PendingLikeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val postId: String,
    val isLike: Boolean, // true = like, false = unlike
    val timestamp: Long
)

// Queue for Comments waiting to upload
@Entity(tableName = "pending_comments")
data class PendingCommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val postId: String,
    val text: String,
    val timestamp: Long
)