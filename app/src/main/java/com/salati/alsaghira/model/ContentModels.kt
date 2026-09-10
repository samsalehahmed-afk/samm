package com.salati.alsaghira.model

/** A single ayah (verse) of Al-Fatiha, shown and read progressively. */
data class AyahItem(
    val order: Int,
    val arabicText: String,
    val audioAsset: String? = null
)

/** A dhikr said during one stage of the prayer. */
data class DhikrItem(
    val id: String,
    val relatedStage: String,
    val arabicText: String,
    val meaningForChildren: String? = null,
    val audioAsset: String? = null
)

/** A single question/answer entry in the assistant's local knowledge base. */
data class FaqEntry(
    val id: String,
    val keywords: List<String>,
    val question: String,
    val answer: String
)
