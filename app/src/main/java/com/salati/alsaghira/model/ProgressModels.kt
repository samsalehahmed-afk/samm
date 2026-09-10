package com.salati.alsaghira.model

/**
 * Snapshot of the child's progress. Persisted locally via [com.salati.alsaghira.data.local.ProgressStore].
 * No personal information is ever stored here — only learning progress.
 */
data class UserProgress(
    val totalStars: Int = 0,
    val completedLessonIds: Set<String> = emptySet(),
    val completedQuizIds: Set<String> = emptySet(),
    val quizCorrectCount: Int = 0,
    val quizAttemptCount: Int = 0,
    val badges: Set<String> = emptySet()
) {
    /** Overall completion percentage across the fixed MVP curriculum. */
    fun completionPercent(totalLessons: Int): Int {
        if (totalLessons == 0) return 0
        return ((completedLessonIds.size.toFloat() / totalLessons) * 100).toInt().coerceIn(0, 100)
    }
}
