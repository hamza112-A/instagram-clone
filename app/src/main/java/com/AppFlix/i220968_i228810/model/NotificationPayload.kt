package com.AppFlix.i220968_i228810.model

data class NotificationPayload(
    val type: String = "", // e.g. "message", "follow_request", "screenshot"
    val title: String = "",
    val body: String = "",
    val targetUid: String = "",
    val data: Map<String, String> = emptyMap(),
    val sentAt: Long = 0L
)
