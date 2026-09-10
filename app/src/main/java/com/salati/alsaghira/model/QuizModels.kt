package com.salati.alsaghira.model

enum class QuizType {
    MULTIPLE_CHOICE,
    ORDER_STEPS,
    IMAGE_CHOICE,
    TRUE_FALSE
}

/**
 * A single quiz question. Not every field is used by every [QuizType]:
 * - MULTIPLE_CHOICE / TRUE_FALSE / IMAGE_CHOICE use [options] + [correctAnswerIndex]
 * - ORDER_STEPS uses [options] as the shuffled items and [correctOrder] as the answer
 */
data class QuizQuestion(
    val id: String,
    val type: QuizType,
    val question: String,
    val options: List<String> = emptyList(),
    val optionImages: List<String>? = null,
    val correctAnswerIndex: Int = -1,
    val correctOrder: List<Int>? = null,
    val explanation: String? = null
)
