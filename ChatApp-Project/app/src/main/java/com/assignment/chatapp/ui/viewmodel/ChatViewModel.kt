package com.assignment.chatapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.chatapp.domain.usecase.GenerateAutoReplyUseCase
import com.assignment.chatapp.domain.usecase.GetMessagesUseCase
import com.assignment.chatapp.domain.usecase.SendMessageUseCase
import com.assignment.chatapp.ui.uistates.ChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the chat screen.
 * Depends ONLY on domain layer (use cases), NOT on data layer.
 * 
 * This is the presentation layer - handles UI state and user interactions.
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val generateAutoReplyUseCase: GenerateAutoReplyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        // Observe messages from domain
        viewModelScope.launch {
            getMessagesUseCase().collect { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
    }

    /**
     * Updates the current input text.
     */
    fun updateInput(text: String) {
        _uiState.update { it.copy(currentInput = text) }
    }

    /**
     * Sends a message using the SendMessageUseCase.
     * The use case handles validation and automated reply logic.
     */
    fun sendMessage() {
        val text = _uiState.value.currentInput
        
        viewModelScope.launch {
            // Use case handles all business logic
            sendMessageUseCase(text)

            // Clear input field after message is sent
            _uiState.update { it.copy(currentInput = "") }

            // Initiate automatic reply after 5 seconds
            launch {
                delay(5000)
                generateAutoReplyUseCase(text)
            }
        }
    }
}
