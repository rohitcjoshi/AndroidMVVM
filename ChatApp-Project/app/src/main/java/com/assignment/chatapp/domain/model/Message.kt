package com.assignment.chatapp.domain.model

/**
 * Domain model representing a chat message.
 *
 * @property id Unique identifier for the message
 * @property text Content of the message
 * @property isUserMessage True if sent by user, false if automated reply
 * @property timestamp When the message was created (milliseconds since epoch)
 */
data class Message(
    val id: String,
    val text: String,
    val isUserMessage: Boolean,
    val timestamp: Long
)
