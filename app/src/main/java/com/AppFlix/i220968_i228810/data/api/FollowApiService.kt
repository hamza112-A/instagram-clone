package com.AppFlix.i220968_i228810.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface FollowApiService {

    // Follow Actions
    @FormUrlEncoded
    @POST("follow.php")
    suspend fun manageFollow(
        @Field("action") action: String, // request, accept, reject, unfollow
        @Field("userId") userId: String,
        @Field("targetId") targetId: String
    ): Response<GenericResponse>

    // Get Lists
    // CHANGED: Returns FollowUsersResponse instead of SearchUsersResponse
    // because follow.php does not return email/dob.
    @GET("follow.php")
    suspend fun getFollowData(
        @Query("action") action: String, // followers, following, requests
        @Query("userId") userId: String
    ): Response<FollowUsersResponse>

    // Check Status
    @GET("follow.php?action=check_status")
    suspend fun checkFollowStatus(
        @Query("userId") userId: String,
        @Query("targetId") targetId: String
    ): Response<FollowStatusResponse>

    // Profile Update
    @Multipart
    @POST("profile.php")
    suspend fun updateProfileImage(
        @Part("userId") userId: RequestBody,
        @Part("type") type: RequestBody, // "profile" or "cover"
        @Part image: MultipartBody.Part
    ): Response<ProfileUpdateResponse>
}

// --- Response Data Classes ---

data class FollowStatusResponse(val success: Boolean, val status: String)
data class ProfileUpdateResponse(val success: Boolean, val url: String?)

// NEW: Specific DTOs for Follow Lists (handles missing email/dob)
data class FollowUsersResponse(
    val success: Boolean,
    val users: List<FollowUserDto>?
)

data class FollowUserDto(
    val id: Int,
    val username: String,
    val first_name: String?,
    val last_name: String?,
    val profile_image_url: String?
)