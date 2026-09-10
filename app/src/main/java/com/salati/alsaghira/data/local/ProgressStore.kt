package com.salati.alsaghira.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.salati.alsaghira.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "salati_progress")

/**
 * Stores the child's progress locally on-device only.
 * No personal data (name, phone, email, location) is ever stored here —
 * only lesson/quiz completion and star counts, exactly as required for
 * the "child profile" that needs no personal information.
 */
class ProgressStore(private val context: Context) {

    private object Keys {
        val STARS = intPreferencesKey("total_stars")
        val COMPLETED_LESSONS = stringSetPreferencesKey("completed_lessons")
        val COMPLETED_QUIZZES = stringSetPreferencesKey("completed_quizzes")
        val QUIZ_CORRECT = intPreferencesKey("quiz_correct_count")
        val QUIZ_ATTEMPTS = intPreferencesKey("quiz_attempt_count")
        val BADGES = stringSetPreferencesKey("badges")
    }

    val progressFlow: Flow<UserProgress> = context.dataStore.data.map { prefs ->
        UserProgress(
            totalStars = prefs[Keys.STARS] ?: 0,
            completedLessonIds = prefs[Keys.COMPLETED_LESSONS] ?: emptySet(),
            completedQuizIds = prefs[Keys.COMPLETED_QUIZZES] ?: emptySet(),
            quizCorrectCount = prefs[Keys.QUIZ_CORRECT] ?: 0,
            quizAttemptCount = prefs[Keys.QUIZ_ATTEMPTS] ?: 0,
            badges = prefs[Keys.BADGES] ?: emptySet()
        )
    }

    suspend fun addStars(amount: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.STARS] = (prefs[Keys.STARS] ?: 0) + amount
        }
    }

    suspend fun markLessonCompleted(lessonId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.COMPLETED_LESSONS] ?: emptySet()
            prefs[Keys.COMPLETED_LESSONS] = current + lessonId
        }
    }

    suspend fun recordQuizAnswer(correct: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.QUIZ_ATTEMPTS] = (prefs[Keys.QUIZ_ATTEMPTS] ?: 0) + 1
            if (correct) prefs[Keys.QUIZ_CORRECT] = (prefs[Keys.QUIZ_CORRECT] ?: 0) + 1
        }
    }

    suspend fun markQuizCompleted(quizId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.COMPLETED_QUIZZES] ?: emptySet()
            prefs[Keys.COMPLETED_QUIZZES] = current + quizId
        }
    }

    suspend fun unlockBadge(badgeId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.BADGES] ?: emptySet()
            prefs[Keys.BADGES] = current + badgeId
        }
    }

    suspend fun resetAll() {
        context.dataStore.edit { it.clear() }
    }
}
