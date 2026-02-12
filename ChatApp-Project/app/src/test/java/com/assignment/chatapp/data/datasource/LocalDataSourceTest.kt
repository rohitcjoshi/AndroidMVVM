package com.assignment.chatapp.data.datasource

import com.assignment.chatapp.domain.model.Message
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {

    private lateinit var dataSource: ChatMessagesDataSource

    @Before
    fun setup() {
        dataSource = ChatMessagesDataSource()
    }

    @Test
    fun `initially messages are empty`() = runTest {
        val messages = dataSource.observeMessages().first()
        assertTrue(messages.isEmpty())
    }

    @Test
    fun `addMessage adds message to list`() {
        val message = Message(
            id = "1",
            text = "Hello",
            isUserMessage = true,
            timestamp = 100L
        )

        dataSource.addMessage(message)

        val messages = dataSource.getMessages()
        assertEquals(1, messages.size)
        assertEquals(message, messages[0])
    }

    @Test
    fun `addMessage maintains order`() {
        val message1 = Message("1", "First", true, 100L)
        val message2 = Message("2", "Second", false, 200L)
        val message3 = Message("3", "Third", true, 300L)

        dataSource.addMessage(message1)
        dataSource.addMessage(message2)
        dataSource.addMessage(message3)

        val messages = dataSource.getMessages()
        assertEquals(3, messages.size)
        assertEquals("First", messages[0].text)
        assertEquals("Second", messages[1].text)
        assertEquals("Third", messages[2].text)
    }

    @Test
    fun `observeMessages emits updates`() = runTest {
        val initialMessages = dataSource.observeMessages().first()
        assertTrue(initialMessages.isEmpty())

        val message = Message("1", "Test", true, 100L)
        dataSource.addMessage(message)

        val updatedMessages = dataSource.observeMessages().first()
        assertEquals(1, updatedMessages.size)
        assertEquals(message, updatedMessages[0])
    }

    @Test
    fun `getMessages returns current snapshot`() {
        val message1 = Message("1", "First", true, 100L)
        val message2 = Message("2", "Second", false, 200L)

        dataSource.addMessage(message1)
        dataSource.addMessage(message2)

        val snapshot = dataSource.getMessages()
        assertEquals(2, snapshot.size)
    }
}
