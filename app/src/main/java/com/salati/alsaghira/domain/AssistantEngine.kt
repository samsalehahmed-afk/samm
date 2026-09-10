package com.salati.alsaghira.domain

import com.salati.alsaghira.model.FaqEntry

/**
 * "معلّم الصلاة" assistant — NOT a mufti and must never invent religious rulings.
 *
 * MVP behavior (as required):
 * - Matches the child's question against a fixed local knowledge base (faq.json).
 * - If no confident match is found, returns the safe fallback answer telling the
 *   child to ask a parent or teacher, instead of guessing.
 *
 * This class is intentionally simple keyword matching so it is easy to audit —
 * there is no generative model involved in the MVP, so it cannot produce an
 * answer that isn't already reviewed and stored in faq.json.
 *
 * Future extension point: replace [answer] with a call to a real AI model
 * (e.g. Gemini) while keeping [fallbackAnswer] as a guardrail for low-confidence
 * responses. Core lessons must never depend on that being available.
 */
class AssistantEngine(private val faqEntries: List<FaqEntry>) {

    companion object {
        const val fallbackAnswer = "لا أعرف الإجابة عن هذا السؤال، اسأل والديك أو معلمك."
    }

    fun answer(childQuestion: String): String {
        val normalized = normalize(childQuestion)
        if (normalized.isBlank()) return fallbackAnswer

        var bestMatch: FaqEntry? = null
        var bestScore = 0

        for (entry in faqEntries) {
            val score = entry.keywords.count { keyword ->
                normalized.contains(normalize(keyword))
            }
            if (score > bestScore) {
                bestScore = score
                bestMatch = entry
            }
        }

        return if (bestMatch != null && bestScore > 0) bestMatch.answer else fallbackAnswer
    }

    private fun normalize(text: String): String =
        text.trim().replace("أ", "ا").replace("إ", "ا").replace("آ", "ا").replace("ة", "ه")
}
