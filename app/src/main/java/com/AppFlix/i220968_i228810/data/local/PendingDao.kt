package com.AppFlix.i220968_i228810.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PendingDao {
    // --- Posts ---
    @Insert
    suspend fun insertPost(post: PendingPostEntity)

    @Query("SELECT * FROM pending_posts")
    suspend fun getAllPendingPosts(): List<PendingPostEntity>

    @Delete
    suspend fun deletePost(post: PendingPostEntity)

    // --- Stories ---
    @Insert
    suspend fun insertStory(story: PendingStoryEntity)

    @Query("SELECT * FROM pending_stories")
    suspend fun getAllPendingStories(): List<PendingStoryEntity>

    @Delete
    suspend fun deleteStory(story: PendingStoryEntity)

    // --- Likes (Fixes "PendingLikeEntity" error) ---
    @Insert
    suspend fun insertLike(like: PendingLikeEntity)

    @Query("SELECT * FROM pending_likes")
    suspend fun getAllPendingLikes(): List<PendingLikeEntity>

    @Delete
    suspend fun deleteLike(like: PendingLikeEntity)

    // --- Comments (Fixes "PendingCommentEntity" error) ---
    @Insert
    suspend fun insertComment(comment: PendingCommentEntity)

    @Query("SELECT * FROM pending_comments")
    suspend fun getAllPendingComments(): List<PendingCommentEntity>

    @Delete
    suspend fun deleteComment(comment: PendingCommentEntity)

    // --- Messages (Fixes "msg", "text", "senderId" errors) ---
    // This grabs messages from the MAIN message table that haven't been synced yet
    @Query("SELECT * FROM messages WHERE syncStatus = 1")
    suspend fun getPendingMessages(): List<MessageEntity>
}