package com.AppFlix.i220968_i228810.data

import android.content.Context
import com.AppFlix.i220968_i228810.model.UserProfile
import com.AppFlix.i220968_i228810.presence.PresenceManager
import com.google.firebase.messaging.FirebaseMessaging

class SessionManager(private val context: Context) {

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val presenceManager = PresenceManager.getInstance()
    private val userRepository = UserRepository()

    companion object {
        private const val TAG = "SessionManager"
        private const val PREF_NAME = "socially_session"
        private const val KEY_UID = "uid"
        private const val KEY_USERNAME = "username"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_DOB = "dob"
        private const val KEY_EMAIL = "email"
        private const val KEY_PROFILE_IMAGE_URL = "profile_image_url"
        private const val KEY_COVER_PHOTO_URL = "cover_photo_url"
        private const val KEY_BIO = "bio" // New Key
        private const val KEY_CREATED_AT = "created_at"
    }

    fun saveUserProfile(profile: UserProfile) {
        preferences.edit()
            .putString(KEY_UID, profile.uid)
            .putString(KEY_USERNAME, profile.username)
            .putString(KEY_FIRST_NAME, profile.firstName)
            .putString(KEY_LAST_NAME, profile.lastName)
            .putString(KEY_DOB, profile.dateOfBirth)
            .putString(KEY_EMAIL, profile.email)
            .putString(KEY_PROFILE_IMAGE_URL, profile.profileImageUrl)
            .putString(KEY_COVER_PHOTO_URL, profile.coverPhotoUrl)
            .putString(KEY_BIO, profile.bio) // Save Bio
            .putLong(KEY_CREATED_AT, profile.createdAt)
            .apply()

        presenceManager.startHeartbeat(profile.uid)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userRepository.updateFCMToken(profile.uid, task.result) {}
            }
        }
    }

    fun getUserProfile(): UserProfile? {
        val uid = preferences.getString(KEY_UID, null) ?: return null
        return UserProfile(
            uid = uid,
            username = preferences.getString(KEY_USERNAME, "") ?: "",
            firstName = preferences.getString(KEY_FIRST_NAME, "") ?: "",
            lastName = preferences.getString(KEY_LAST_NAME, "") ?: "",
            dateOfBirth = preferences.getString(KEY_DOB, "") ?: "",
            email = preferences.getString(KEY_EMAIL, "") ?: "",
            profileImageUrl = preferences.getString(KEY_PROFILE_IMAGE_URL, "") ?: "",
            coverPhotoUrl = preferences.getString(KEY_COVER_PHOTO_URL, "") ?: "",
            bio = preferences.getString(KEY_BIO, "") ?: "", // Retrieve Bio
            createdAt = preferences.getLong(KEY_CREATED_AT, 0L)
        )
    }

    fun setLastVanishClearTime(chatId: String, time: Long) {
        preferences.edit().putLong("vanish_clear_$chatId", time).apply()
    }

    fun getLastVanishClearTime(chatId: String): Long {
        return preferences.getLong("vanish_clear_$chatId", 0L)
    }

    fun clear() {
        preferences.edit().clear().apply()
        presenceManager.stopHeartbeat()
    }
}