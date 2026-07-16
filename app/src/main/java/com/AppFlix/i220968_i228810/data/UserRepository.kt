package com.AppFlix.i220968_i228810.data

import com.AppFlix.i220968_i228810.FirebasePaths
import com.AppFlix.i220968_i228810.data.api.ApiClient
import com.AppFlix.i220968_i228810.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository {
    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    fun getUserProfile(uid: String, onResult: (UserProfile?) -> Unit): ValueEventListener {
        val ref = database.child(FirebasePaths.USERS).child(uid)
        val listener = object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                onResult(snapshot.getValue(UserProfile::class.java))
            }
            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                onResult(null)
            }
        }
        ref.addValueEventListener(listener)
        return listener
    }

    // One-time fetch without listener (for inbox)
    fun getUserProfileOnce(uid: String, onResult: (UserProfile?) -> Unit) {
        database.child(FirebasePaths.USERS).child(uid).get()
            .addOnSuccessListener { snapshot ->
                onResult(snapshot.getValue(UserProfile::class.java))
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun updateProfile(uid: String, profile: UserProfile, onComplete: (Boolean) -> Unit) {
        database.child(FirebasePaths.USERS).child(uid).setValue(profile)
            .addOnCompleteListener { onComplete(it.isSuccessful) }
    }

    fun updateProfileImage(uid: String, imageUrl: String, onComplete: (Boolean) -> Unit) {
        database.child(FirebasePaths.USERS).child(uid).child("profileImageUrl").setValue(imageUrl)
            .addOnCompleteListener { onComplete(it.isSuccessful) }
    }

    // Fixed: Added imports for CoroutineScope, Dispatchers, and ApiClient
    fun updateFCMToken(uid: String, token: String, onComplete: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.authApi.updateFcmToken(uid, token)
                val success = response.isSuccessful && response.body()?.success == true
                withContext(Dispatchers.Main) { onComplete(success) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onComplete(false) }
            }
        }
    }

    // Follow relationships (Legacy/Firebase implementation)
    fun sendFollowRequest(fromUid: String, toUid: String, onComplete: (Boolean) -> Unit) {
        database.child(FirebasePaths.FOLLOW_REQUESTS).child(toUid).child(fromUid).setValue(true)
            .addOnCompleteListener { onComplete(it.isSuccessful) }
    }
    fun acceptFollowRequest(fromUid: String, toUid: String, onComplete: (Boolean) -> Unit) {
        val batch = database
        batch.child(FirebasePaths.FOLLOW_REQUESTS).child(toUid).child(fromUid).removeValue()
        batch.child(FirebasePaths.FOLLOWERS).child(toUid).child(fromUid).setValue(true)
        batch.child(FirebasePaths.FOLLOWING).child(fromUid).child(toUid).setValue(true)
        onComplete(true)
    }
    fun declineFollowRequest(fromUid: String, toUid: String, onComplete: (Boolean) -> Unit) {
        database.child(FirebasePaths.FOLLOW_REQUESTS).child(toUid).child(fromUid).removeValue()
            .addOnCompleteListener { onComplete(it.isSuccessful) }
    }
    fun getFollowers(uid: String, onResult: (List<String>) -> Unit): ValueEventListener {
        val ref = database.child(FirebasePaths.FOLLOWERS).child(uid)
        val listener = object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val followers = snapshot.children.mapNotNull { it.key }
                onResult(followers)
            }
            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                onResult(emptyList())
            }
        }
        ref.addValueEventListener(listener)
        return listener
    }
    fun getFollowing(uid: String, onResult: (List<String>) -> Unit): ValueEventListener {
        val ref = database.child(FirebasePaths.FOLLOWING).child(uid)
        val listener = object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val following = snapshot.children.mapNotNull { it.key }
                onResult(following)
            }
            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                onResult(emptyList())
            }
        }
        ref.addValueEventListener(listener)
        return listener
    }

    // Get all users (for testing/user selection)
    fun getAllUsers(onResult: (List<UserProfile>) -> Unit) {
        database.child(FirebasePaths.USERS).get()
            .addOnSuccessListener { snapshot ->
                val users = snapshot.children.mapNotNull { it.getValue(UserProfile::class.java) }
                onResult(users)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}