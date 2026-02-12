package com.assignment.chatapp.domain.usecase

import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.repository.ChatRepository
import java.util.UUID

class GenerateAutoReplyUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(originalText: String) {
        val reply = Message(
            id = UUID.randomUUID().toString(),
            text = "Got the message: $originalText",
            isUserMessage = false,
            timestamp = System.currentTimeMillis()
        )

        repository.addMessage(reply)
    }
}