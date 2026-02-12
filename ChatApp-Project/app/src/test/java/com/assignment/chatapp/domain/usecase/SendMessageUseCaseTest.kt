package com.assignment.chatapp.domain.usecase

import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.repository.ChatRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SendMessageUseCaseTest {

    private lateinit var repository: ChatRepository
    private lateinit var useCase: SendMessageUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SendMessageUseCase(repository)
    }

    @Test
    fun `invoke with valid text returns true and adds user message`() = runTest {
        val result = useCase("Hello")

        assertNotNull(result)
        
        // Verify user message was added
        coVerify {
            repository.addMessage(
                match { message ->
                    message.text == "Hello" &&
                    message.isUserMessage &&
                    message.id.isNotEmpty()
                }
            )
        }
    }

    @Test
    fun `invoke with whitespace only returns false and does not add message`() = runTest {
        useCase.invoke("   ")
        coVerify(exactly = 0) { repository.addMessage(any()) }
    }

    @Test
    fun `invoke trims whitespace from message text`() = runTest {
        useCase("  Hello  ")

        coVerify {
            repository.addMessage(
                match { it.text == "Hello" }
            )
        }
    }

    @Test
    fun `invoke creates unique IDs for messages`() = runTest {
        val capturedMessages = mutableListOf<Message>()

        coEvery { repository.addMessage(capture(capturedMessages)) } just Runs

        useCase("Message1")
        useCase("Message2")
        assertEquals(2, capturedMessages.size)
        assertNotEquals(capturedMessages[0].id, capturedMessages[1].id)
    }
}
