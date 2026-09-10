package com.salati.alsaghira.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salati.alsaghira.data.local.ProgressStore
import com.salati.alsaghira.model.UserProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Total number of MVP lessons, used to compute the home-screen progress bar. */
const val TOTAL_MVP_LESSONS = 2 // Wudu + Prayer (Fatiha/Adhkar tracked as part of Prayer)

class ProgressViewModel(private val store: ProgressStore) : ViewModel() {

    val progress: StateFlow<UserProgress> = store.progressFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserProgress()
    )

    fun completeLesson(lessonId: String, starsAwarded: Int = 3) {
        viewModelScope.launch {
            store.markLessonCompleted(lessonId)
            store.addStars(starsAwarded)
            maybeUnlockBadges()
        }
    }

    fun recordQuizAnswer(correct: Boolean) {
        viewModelScope.launch {
            store.recordQuizAnswer(correct)
            if (correct) store.addStars(1)
        }
    }

    fun completeQuiz(quizSetId: String) {
        viewModelScope.launch {
            store.markQuizCompleted(quizSetId)
            store.addStars(3)
            maybeUnlockBadges()
        }
    }

    fun resetProgress() {
        viewModelScope.launch { store.resetAll() }
    }

    private suspend fun maybeUnlockBadges() {
        val current = progress.value
        if (current.completedLessonIds.size >= TOTAL_MVP_LESSONS) {
            store.unlockBadge("badge_all_lessons")
        }
    }
}
