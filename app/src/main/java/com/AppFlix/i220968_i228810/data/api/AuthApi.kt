package com.AppFlix.i220968_i228810.data.api

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("signup.php")
    suspend fun signup(@Body request: SignupRequest): Response<AuthResponse>

    @POST("logout.php")
    suspend fun logout(): Response<GenericResponse>

    @POST("reset_password.php")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<GenericResponse>

    @GET("search.php")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("current_user_id") currentUserId: String
    ): Response<SearchUsersResponse>

    @GET("users.php?action=get_all")
    suspend fun getAllUsers(
        @Query("current_user_id") currentUserId: String
    ): Response<SearchUsersResponse>

    // NEW: Update Profile Text Data
    @Multipart
    @POST("update_profile.php")
    suspend fun updateProfile(
        @Part("user_id") userId: RequestBody,
        @Part("username") username: RequestBody,
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("bio") bio: RequestBody // <--- ADD THIS
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("update_fcm.php")
    suspend fun updateFcmToken(
        @Field("user_id") userId: String,
        @Field("token") token: String
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("screenshot.php")
    suspend fun sendScreenshotAlert(
        @Field("senderId") senderId: String,
        @Field("targetId") targetId: String,
        @Field("senderName") senderName: String
    ): Response<GenericResponse>

    // NEW: Call Signaling Endpoint
    @FormUrlEncoded
    @POST("call.php")
    suspend fun initiateCall(
        @Field("callerId") callerId: String,
        @Field("targetId") targetId: String,
        @Field("callerName") callerName: String,
        @Field("callType") callType: String,
        @Field("channelName") channelName: String
    ): Response<GenericResponse>
}
data class LoginRequest(val email: String, val password: String)

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val dob: String
)

data class AuthResponse(
    val success: Boolean,
    val message: String?,
    val user: UserDto?
)

data class SearchUsersResponse(
    val success: Boolean,
    val users: List<UserDto>?
)

// --- UPDATED USER DTO ---
data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String?,
    val last_name: String?,
    val dob: String?,
    val profile_image_url: String? // Added this field to fix the error
)