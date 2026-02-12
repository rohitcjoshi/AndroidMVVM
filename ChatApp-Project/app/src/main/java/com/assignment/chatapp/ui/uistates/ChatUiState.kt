package com.assignment.chatapp.ui.uistates

import com.assignment.chatapp.domain.model.Message

/**
 * UI state for the chat screen.
 */
data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val currentInput: String = "",
)