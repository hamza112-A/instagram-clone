package com.AppFlix.i220968_i228810.model

data class PresenceSnapshot(
    val uid: String = "",
    val status: String = "offline", // "online", "offline", "on_call"
    val lastActive: Long = 0L
)
