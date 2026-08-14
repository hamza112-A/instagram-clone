package com.AppFlix.i220968_i228810.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY createdAt ASC")
    fun getMessages(chatId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChats(chats: List<ChatEntity>)

    @Query("SELECT * FROM chats ORDER BY lastMessageTime DESC")
    fun getChats(): Flow<List<ChatEntity>>

    @Query("DELETE FROM messages")
    suspend fun clearAll()

    @Query("DELETE FROM chats")
    suspend fun clearChats()

    // --- UPDATED METHODS FOR SYNC FIX ---

    @Query("UPDATE messages SET serverId = :serverId, mediaUrl = :mediaUrl, syncStatus = 0 WHERE localId = :localId")
    suspend fun updateMessageSynced(localId: Long, serverId: Int, mediaUrl: String)

    @Query("UPDATE messages SET syncStatus = 1 WHERE localId = :localId")
    suspend fun markAsPending(localId: Long)

    @Query("UPDATE messages SET serverId = :serverId, syncStatus = 0 WHERE localId = :localId")
    suspend fun updateServerId(localId: Long, serverId: Int)

    @Query("UPDATE messages SET isDeleted = 1 WHERE localId = :localId")
    suspend fun markDeleted(localId: Long)

    @Query("UPDATE messages SET text = :text, mediaUrl = :mediaUrl, isDeleted = :isDeleted WHERE serverId = :id")
    suspend fun updateSyncedMessage(id: Int, text: String, mediaUrl: String, isDeleted: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM messages WHERE serverId = :id)")
    suspend fun hasMessageWithServerId(id: Int): Boolean

    // --- THIS IS THE CRITICAL FIX ---
    // We now check for syncStatus = 1 (Offline Queue) OR syncStatus = 2 (Currently Uploading)
    @Query("""
        SELECT * FROM messages 
        WHERE chatId = :chatId 
        AND senderId = :senderId 
        AND text = :text 
        AND (syncStatus = 1 OR syncStatus = 2) 
        AND ABS(createdAt - :serverTime) < 10000 
        LIMIT 1
    """)
    suspend fun findPendingMessage(chatId: String, senderId: String, text: String, serverTime: Long): MessageEntity?

    @Query("DELETE FROM messages WHERE chatId = :chatId AND (type = 'VANISH' OR type = 'VANISH_IMAGE')")
    suspend fun deleteVanishMessages(chatId: String)
}