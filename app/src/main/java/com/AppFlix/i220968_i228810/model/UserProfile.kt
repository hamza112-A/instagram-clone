package com.AppFlix.i220968_i228810.model

data class UserProfile(
    val uid: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val coverPhotoUrl: String = "",
    val bio: String = "", // Added Bio
    val createdAt: Long = 0L,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val onlineStatus: String = "offline",
    val fcmToken: String = ""
)