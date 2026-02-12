package com.assignment.chatapp.di

import com.assignment.chatapp.data.repository.ChatRepositoryImpl
import com.assignment.chatapp.domain.repository.ChatRepository
import com.assignment.chatapp.domain.usecase.GenerateAutoReplyUseCase
import com.assignment.chatapp.domain.usecase.GetMessagesUseCase
import com.assignment.chatapp.domain.usecase.SendMessageUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetMessagesUseCase(chatRepository: ChatRepository): GetMessagesUseCase {
            return GetMessagesUseCase(chatRepository)
        }

        @Provides
        @Singleton
        fun provideSendMessageUseCase(chatRepository: ChatRepository): SendMessageUseCase {
            return SendMessageUseCase(chatRepository)
        }

        @Provides
        @Singleton
        fun provideGenerateAutoReplyUseCase(chatRepository: ChatRepository): GenerateAutoReplyUseCase {
            return GenerateAutoReplyUseCase(chatRepository)
        }
    }
}
