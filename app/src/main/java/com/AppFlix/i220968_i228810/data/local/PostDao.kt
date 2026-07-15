package com.AppFlix.i220968_i228810.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY createdAt DESC")
    suspend fun getAllPosts(): List<PostEntity>

    // Helper to get a single post for like updates
    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPostById(postId: String): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    // For optimistic like updates
    @Query("UPDATE posts SET likesCount = :newCount, isLikedByCurrentUser = :isLiked WHERE id = :postId")
    suspend fun updateLikeStatus(postId: String, newCount: Int, isLiked: Boolean)

    @Query("DELETE FROM posts")
    suspend fun clearAll()
}