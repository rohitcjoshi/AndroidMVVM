package com.assignment.chatapp.domain.usecase

import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.repository.ChatRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetMessagesUseCaseTest {

    private lateinit var repository: ChatRepository
    private lateinit var useCase: GetMessagesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetMessagesUseCase(repository)
    }

    @Test
    fun `invoke returns flow from repository`() = runTest {
        val messages = listOf(
            Message(
                id = "1",
                text = "Hello",
                isUserMessage = true,
                timestamp = 100L
            )
        )
        
        every { repository.observeMessages() } returns flowOf(messages)

        val result = useCase().first()

        assertEquals(messages, result)
    }

    @Test
    fun `invoke returns empty list when no messages`() = runTest {
        every { repository.observeMessages() } returns flowOf(emptyList())

        val result = useCase().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke returns messages in repository order`() = runTest {
        val messages = listOf(
            Message("1", "First", true, 100L),
            Message("2", "Second", false, 200L),
            Message("3", "Third", true, 300L)
        )
        
        every { repository.observeMessages() } returns flowOf(messages)

        val result = useCase().first()

        assertEquals(3, result.size)
        assertEquals("First", result[0].text)
        assertEquals("Second", result[1].text)
        assertEquals("Third", result[2].text)
    }
}
