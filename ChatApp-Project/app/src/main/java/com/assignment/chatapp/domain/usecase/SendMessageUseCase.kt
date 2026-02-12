package com.assignment.chatapp.domain.usecase

import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.repository.ChatRepository
import java.util.UUID

/**
 * Use case for sending a message.
 * Encapsulates the business logic:
 * 1. Validate message is not empty
 * 2. Create and save user message
 * 3. Schedule automated reply after 5 seconds
 */
class SendMessageUseCase(
    private val repository: ChatRepository
) {
    /**
     * Sends a message if valid.
     * 
     * @param text The message text to send
     * @return true if message was sent, false if invalid (empty)
     */
    suspend operator fun invoke(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return

        val message = Message(
            id = UUID.randomUUID().toString(),
            text = trimmed,
            isUserMessage = true,
            timestamp = System.currentTimeMillis()
        )

        repository.addMessage(message)
    }
}
