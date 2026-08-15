package com.AppFlix.i220968_i228810.model

data class FollowRequest(
    val fromUid: String = "",
    val toUid: String = "",
    val sentAt: Long = 0L
)
