package com.salati.alsaghira.model

/**
 * A single step inside a lesson (e.g. one step of Wudu, or one movement of prayer).
 * Kept generic so both Wudu and Prayer lessons reuse the same UI.
 */
data class LessonStep(
    val id: String,
    val order: Int,
    val title: String,
    val shortExplanation: String,
    val spokenText: String? = null,
    val imageAsset: String? = null,
    val audioAsset: String? = null
)

/**
 * A full lesson made of ordered steps (Wudu lesson, Prayer lesson, etc).
 */
data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val steps: List<LessonStep>
)
