package com.AppFlix.i220968_i228810.data.api

import retrofit2.Response
import retrofit2.http.*

interface PresenceApiService {

    @FormUrlEncoded
    @POST("presence.php")
    suspend fun sendHeartbeat(
        @Field("action") action: String = "heartbeat",
        @Field("userId") userId: String
    ): Response<GenericResponse>

    // NEW: Instant Offline
    @FormUrlEncoded
    @POST("presence.php")
    suspend fun goOffline(
        @Field("action") action: String = "go_offline",
        @Field("userId") userId: String
    ): Response<GenericResponse>

    @GET("presence.php?action=get_status")
    suspend fun getUserStatuses(
        @Query("userIds") userIds: String
    ): Response<PresenceResponse>
}

data class PresenceResponse(
    val success: Boolean,
    val statuses: List<PresenceDto>
)

data class PresenceDto(
    val uid: String,
    val status: String, // "online" or "offline"
    val lastActive: Long
)