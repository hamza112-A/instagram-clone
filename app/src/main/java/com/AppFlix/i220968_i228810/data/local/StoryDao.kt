package com.AppFlix.i220968_i228810.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories WHERE expiresAt > :now ORDER BY createdAt DESC")
    suspend fun getActiveStories(now: Long): List<StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stories: List<StoryEntity>)

    @Query("DELETE FROM stories")
    suspend fun clearAll()
}