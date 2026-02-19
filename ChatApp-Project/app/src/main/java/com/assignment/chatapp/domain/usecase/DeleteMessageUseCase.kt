package com.assignment.chatapp.domain.usecase

import com.assignment.chatapp.domain.repository.ChatRepository

/**
 * Use case for deleting a message.
 * Encapsulates the business logic for message deletion.
 */
class DeleteMessageUseCase(
    private val repository: ChatRepository
) {
    /**
     * Deletes a message by its ID.
     * 
     * @param messageId The ID of the message to delete
     */
    suspend operator fun invoke(messageId: String) {
        repository.deleteMessage(messageId)
    }
}