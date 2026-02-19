package com.assignment.chatapp.domain.usecase

import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.repository.ChatRepository
import java.util.UUID

class GenerateAutoReplyUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(originalText: String) {
        val trimmedText = originalText.trim()
        if (trimmedText.isEmpty()) return

        val reply = Message(
            id = UUID.randomUUID().toString(),
            text = "Got the message: $trimmedText",
            isUserMessage = false,
            timestamp = System.currentTimeMillis()
        )

        repository.addMessage(reply)
    }
}
