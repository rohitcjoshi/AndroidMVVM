package com.assignment.chatapp.data.repository

import com.assignment.chatapp.data.datasource.ChatMessagesDataSource
import com.assignment.chatapp.domain.model.Message
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChatRepositoryImplTest {

    private lateinit var chatMessagesDataSource: ChatMessagesDataSource
    private lateinit var repository: ChatRepositoryImpl

    @Before
    fun setup() {
        chatMessagesDataSource = mockk(relaxed = true)
        repository = ChatRepositoryImpl(chatMessagesDataSource)
    }

    @Test
    fun `observeMessages returns flow from data source`() = runTest {
        val messages = listOf(
            Message("1", "Hello", true, 100L)
        )
        
        every { chatMessagesDataSource.observeMessages() } returns flowOf(messages)

        val result = repository.observeMessages().first()

        assertEquals(messages, result)
    }

    @Test
    fun `addMessage calls data source`() = runTest {
        val message = Message("1", "Test", true, 100L)

        repository.addMessage(message)

        verify { chatMessagesDataSource.addMessage(message) }
    }

    @Test
    fun `getMessages returns data from data source`() = runTest {
        val messages = listOf(
            Message("1", "First", true, 100L),
            Message("2", "Second", false, 200L)
        )
        
        every { chatMessagesDataSource.getMessages() } returns messages

        val result = repository.getMessages()

        assertEquals(messages, result)
    }

    @Test
    fun `repository properly delegates to data source`() = runTest {
        val message1 = Message("1", "User msg", true, 100L)
        val message2 = Message("2", "Reply", false, 200L)
        
        every { chatMessagesDataSource.observeMessages() } returns flowOf(listOf(message1, message2))

        repository.addMessage(message1)
        repository.addMessage(message2)
        val observed = repository.observeMessages().first()

        verify(exactly = 1) { chatMessagesDataSource.addMessage(message1) }
        verify(exactly = 1) { chatMessagesDataSource.addMessage(message2) }
        assertEquals(2, observed.size)
    }
}
