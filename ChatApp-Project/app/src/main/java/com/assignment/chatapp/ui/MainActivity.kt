package com.assignment.chatapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.chatapp.ui.composables.ChatScreen
import com.assignment.chatapp.ui.theme.ChatAppTheme
import com.assignment.chatapp.ui.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of the app.
 * 
 * This is where we wire up the dependency graph:
 * Data Layer -> Domain Layer -> UI Layer
 * 
 * Using Hilt for dependency injection.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create ViewModel with Hilt
                    val viewModel: ChatViewModel = viewModel()

                    val uiState by viewModel.uiState.collectAsState()

                    ChatScreen(
                        uiState = uiState,
                        onInputChange = viewModel::updateInput,
                        onSendClick = viewModel::sendMessage
                    )
                }
            }
        }
    }
}
