package com.assignment.chatapp.data.datasource

import com.assignment.chatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local chat messages data source for in-memory message storage.
 */
@Singleton
class ChatMessagesDataSource @Inject constructor() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    
    /**
     * Observes all messages.
     */
    fun observeMessages(): Flow<List<Message>> = _messages.asStateFlow()
    
    /**
     * Adds a message to the in-memory storage.
     */
    fun addMessage(message: Message) {
        _messages.value += message
    }
    
    /**
     * Deletes a message from the in-memory storage.
     */
    suspend fun deleteMessage(messageId: String) {
        _messages.value = _messages.value.filter { it.id != messageId }
    }
    
    /**
     * Gets current messages snapshot.
     */
    fun getMessages(): List<Message> = _messages.value
}
