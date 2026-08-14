package com.AppFlix.i220968_i228810.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

// Wrapper for GET /messages.php response
data class GetMessagesResponse(
    val success: Boolean,
    val vanish_mode: Boolean, // <-- This is the key addition
    val messages: List<MessageDto>
)

data class MessageDto(
    val id: Int,
    val sender_id: String,
    val type: String,
    val text_content: String?,
    val media_url: String?,
    val created_at: Long,
    val is_deleted: Int // 0 or 1
)

data class SendMessageResponse(val success: Boolean, val id: Int?, val mediaUrl: String?)
data class EditMessageRequest(val id: Int, val text: String, val senderId: String)
data class DeleteMessageRequest(val id: Int, val senderId: String)

// DTO for Chat List
data class ChatDto(
    val localChatId: String,
    val serverChatId: Int,
    val otherUserId: String,
    val otherUserName: String,
    val otherFullName: String,
    val otherProfileImage: String?,
    val lastMessage: String?,
    val lastMessageTime: Long
)

interface MessageApiService {

    @GET("messages.php")
    suspend fun getMessages(
        @Query("user1") user1: String,
        @Query("user2") user2: String
    ): Response<GetMessagesResponse>

    // New: Toggle Vanish Mode
    @FormUrlEncoded
    @POST("messages.php")
    suspend fun toggleVanishMode(
        @Field("action") action: String = "toggle_vanish",
        @Field("user1") user1: String,
        @Field("user2") user2: String,
        @Field("enable") enable: Boolean
    ): Response<GenericResponse>

    @Multipart
    @POST("messages.php")
    suspend fun sendMessage(
        @Part media: MultipartBody.Part?,
        @Part("senderId") senderId: RequestBody,
        @Part("receiverId") receiverId: RequestBody,
        @Part("text") text: RequestBody,
        @Part("type") type: RequestBody,
        @Part("createdAt") createdAt: RequestBody
    ): Response<SendMessageResponse>

    @PUT("messages.php")
    suspend fun editMessage(@Body req: EditMessageRequest): Response<GenericResponse>

    @HTTP(method = "DELETE", path = "messages.php", hasBody = true)
    suspend fun deleteMessage(@Body req: DeleteMessageRequest): Response<GenericResponse>

    @GET("chats.php")
    suspend fun getUserChats(@Query("userId") userId: String): Response<List<ChatDto>>
}