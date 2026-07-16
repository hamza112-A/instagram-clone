package com.AppFlix.i220968_i228810.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MessageEntity::class,
        ChatEntity::class,
        PostEntity::class,
        StoryEntity::class,
        CommentEntity::class,       // Cache
        PendingPostEntity::class,   // Queue
        PendingStoryEntity::class,  // Queue
        PendingLikeEntity::class,   // Queue
        PendingCommentEntity::class // Queue
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun postDao(): PostDao
    abstract fun storyDao(): StoryDao
    abstract fun commentDao(): CommentDao
    abstract fun pendingDao(): PendingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // This method was missing, causing "Unresolved reference: getDatabase"
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "socially_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}