package com.assignment.chatapp.ui.viewmodel

import app.cash.turbine.test
import com.assignment.chatapp.domain.model.Message
import com.assignment.chatapp.domain.usecase.GenerateAutoReplyUseCase
import com.assignment.chatapp.domain.usecase.GetMessagesUseCase
import com.assignment.chatapp.domain.usecase.SendMessageUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private lateinit var getMessagesUseCase: GetMessagesUseCase
    private lateinit var sendMessageUseCase: SendMessageUseCase

    private lateinit var generateAutoReplyUseCase: GenerateAutoReplyUseCase
    private lateinit var viewModel: ChatViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        getMessagesUseCase = mockk()
        sendMessageUseCase = mockk(relaxed = true)
        generateAutoReplyUseCase = mockk(relaxed = true)
        
        // Default: empty messages
        every { getMessagesUseCase() } returns flowOf(emptyList())
        
        viewModel = ChatViewModel(getMessagesUseCase, sendMessageUseCase, generateAutoReplyUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has empty messages and input`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.messages.isEmpty())
            assertEquals("", state.currentInput)
        }
    }

    @Test
    fun `updateInput updates current input`() = runTest {
        viewModel.updateInput("Hello")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Hello", state.currentInput)
        }
    }

    @Test
    fun `sendMessage calls use case with current input`() = runTest {
        coEvery { sendMessageUseCase("Test message") } just runs
        
        viewModel.updateInput("Test message")
        viewModel.sendMessage()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { sendMessageUseCase("Test message") }
    }

    @Test
    fun `sendMessage clears input when successful`() = runTest {
        coEvery { sendMessageUseCase(any()) }  just runs
        
        viewModel.updateInput("Test")
        viewModel.sendMessage()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("", state.currentInput)
        }
    }

    @Test
    fun `observes messages from use case`() = runTest {
        val messages = listOf(
            Message("1", "Hello", true, 100L),
            Message("2", "Reply", false, 200L)
        )
        
        every { getMessagesUseCase() } returns flowOf(messages)
        
        val newViewModel = ChatViewModel(getMessagesUseCase, sendMessageUseCase, generateAutoReplyUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        newViewModel.uiState.test {
            val state = awaitItem()
            assertEquals(2, state.messages.size)
            assertEquals("Hello", state.messages[0].text)
            assertEquals("Reply", state.messages[1].text)
        }
    }

    @Test
    fun `ViewModel only depends on domain layer`() {
        // This is a design test - ChatViewModel constructor should only
        // take use cases (domain layer), not repositories or data sources
        
        val constructor = ChatViewModel::class.java.constructors[0]
        val paramTypes = constructor.parameterTypes
        
        assertEquals(3, paramTypes.size)
        assertTrue(paramTypes.contains(GetMessagesUseCase::class.java))
        assertTrue(paramTypes.contains(SendMessageUseCase::class.java))
    }
}
