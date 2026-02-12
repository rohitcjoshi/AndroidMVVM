package com.assignment.chatapp.data.mapper

import com.assignment.chatapp.domain.model.Message

/**
 * Mapper between data layer DTOs and domain models.
 * 
 * In this simple app, we're using the same model in both layers.
 * In a real app with API integration, you would map between:
 * - API DTOs (data layer)
 * - Domain models (domain layer)
 * 
 * This keeps the domain layer independent of API changes.
 */
object MessageMapper {
    
    /**
     * Converts a data layer DTO to domain model.
     * Currently 1:1 mapping, but allows for future changes.
     */
    fun toDomain(dataMessage: Message): Message {
        return dataMessage
    }
    
    /**
     * Converts domain model to data layer DTO.
     * Currently 1:1 mapping, but allows for future changes.
     */
    fun toData(domainMessage: Message): Message {
        return domainMessage
    }
    
    /**
     * Converts list of data DTOs to domain models.
     */
    fun toDomainList(dataMessages: List<Message>): List<Message> {
        return dataMessages.map { toDomain(it) }
    }
}
