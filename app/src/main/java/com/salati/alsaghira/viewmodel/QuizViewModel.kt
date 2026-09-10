package com.salati.alsaghira.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.salati.alsaghira.data.repository.ContentRepository
import com.salati.alsaghira.model.QuizQuestion

class QuizViewModel(repository: ContentRepository) : ViewModel() {

    val questions: List<QuizQuestion> = repository.loadQuiz()

    var currentIndex by mutableStateOf(0)
        private set

    var lastAnswerCorrect by mutableStateOf<Boolean?>(null)
        private set

    val currentQuestion get() = questions.getOrNull(currentIndex)
    val isLastQuestion get() = currentIndex == questions.lastIndex
    val isFinished get() = currentIndex >= questions.size

    fun submitMultipleChoiceAnswer(selectedIndex: Int) {
        val question = currentQuestion ?: return
        lastAnswerCorrect = selectedIndex == question.correctAnswerIndex
    }

    fun submitOrderAnswer(orderedIndices: List<Int>) {
        val question = currentQuestion ?: return
        lastAnswerCorrect = orderedIndices == question.correctOrder
    }

    fun submitTrueFalseAnswer(answeredTrue: Boolean) {
        val question = currentQuestion ?: return
        val correctIsTrue = question.correctAnswerIndex == 0
        lastAnswerCorrect = answeredTrue == correctIsTrue
    }

    fun nextQuestion() {
        lastAnswerCorrect = null
        currentIndex++
    }

    fun restart() {
        currentIndex = 0
        lastAnswerCorrect = null
    }
}
