package com.assignment.chatapp.data.repository

import com.assignment.chatapp.data.datasource.ChatMessagesDataSource
import com.assignment.chatapp.data.mapper.MessageMapper
import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of ChatRepository interface.
 * This is in the data layer and implements the domain contract.
 * 
 * Responsibilities:
 * - Coordinate between data sources
 * - Map between data and domain models
 * - Handle data layer logic (caching, sync, etc.)
 */
class ChatRepositoryImpl @Inject constructor(
    private val chatMessagesDataSource: ChatMessagesDataSource
) : ChatRepository {
    
    override fun observeMessages(): Flow<List<Message>> {
        return chatMessagesDataSource.observeMessages()
            .map { messages -> MessageMapper.toDomainList(messages) }
    }
    
    override suspend fun addMessage(message: Message) {
        val dataMessage = MessageMapper.toData(message)
        chatMessagesDataSource.addMessage(dataMessage)
    }
    
    override suspend fun deleteMessage(messageId: String) {
        chatMessagesDataSource.deleteMessage(messageId)
    }
    
    override suspend fun getMessages(): List<Message> {
        val dataMessages = chatMessagesDataSource.getMessages()
        return MessageMapper.toDomainList(dataMessages)
    }
}
