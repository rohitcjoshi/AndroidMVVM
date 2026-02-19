package com.assignment.chatapp.domain.repository

import com.assignment.chatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for chat operations.
 * This is part of the domain layer - NO Android dependencies.
 * 
 * The data layer will implement this interface.
 */
interface ChatRepository {
    /**
     * Observes all messages in the chat.
     * Returns a Flow that emits the current list whenever it changes.
     */
    fun observeMessages(): Flow<List<Message>>
    
    /**
     * Adds a new message to the chat.
     */
    suspend fun addMessage(message: Message)
    
    /**
     * Deletes a message from the chat.
     */
    suspend fun deleteMessage(messageId: String)
    
    /**
     * Gets all messages (snapshot).
     */
    suspend fun getMessages(): List<Message>
}
