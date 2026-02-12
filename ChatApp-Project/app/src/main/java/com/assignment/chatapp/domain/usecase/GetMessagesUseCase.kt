package com.assignment.chatapp.domain.usecase

import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for observing messages.
 * Provides a reactive stream of messages sorted chronologically.
 * 
 * Pure business logic - NO Android dependencies.
 */
class GetMessagesUseCase(
    private val repository: ChatRepository
) {
    /**
     * Observes all messages in chronological order.
     * 
     * @return Flow of message lists, sorted by timestamp (oldest to newest)
     */
    operator fun invoke(): Flow<List<Message>> {
        return repository.observeMessages()
    }
}
