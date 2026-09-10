package com.salati.alsaghira.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.salati.alsaghira.data.repository.ContentRepository
import com.salati.alsaghira.domain.AssistantEngine

data class ChatMessage(val text: String, val isFromChild: Boolean)

class AssistantViewModel(repository: ContentRepository) : ViewModel() {

    private val engine = AssistantEngine(repository.loadFaq())

    var messages = mutableStateOf(
        listOf(
            ChatMessage(
                text = "السلام عليكم! أنا معلّم الصلاة 🤖. اسألني عن الوضوء أو الصلاة!",
                isFromChild = false
            )
        )
    )
        private set

    fun askQuestion(question: String) {
        if (question.isBlank()) return
        val childMsg = ChatMessage(question, isFromChild = true)
        val answer = engine.answer(question)
        val botMsg = ChatMessage(answer, isFromChild = false)
        messages.value = messages.value + childMsg + botMsg
    }
}
