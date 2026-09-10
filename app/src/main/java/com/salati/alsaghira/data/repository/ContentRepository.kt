package com.salati.alsaghira.data.repository

import android.content.Context
import com.salati.alsaghira.model.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader

/**
 * Loads ALL religious / educational content from JSON files under assets/data/.
 *
 * This is intentional: no religious text, ruling, or wording is hard-coded inside
 * UI/Composable files. To review or correct content, edit the JSON files only —
 * no Kotlin changes or rebuild logic needed beyond re-reading the asset.
 *
 * Uses the built-in org.json parser (no extra library needed).
 */
class ContentRepository(private val context: Context) {

    private fun readAsset(fileName: String): String {
        context.assets.open("data/$fileName").use { stream ->
            return stream.bufferedReader(Charsets.UTF_8).use(BufferedReader::readText)
        }
    }

    fun loadWuduLesson(): Lesson = loadLesson("wudu_steps.json")

    fun loadPrayerLesson(): Lesson = loadLesson("prayer_steps.json")

    private fun loadLesson(fileName: String): Lesson {
        val json = JSONObject(readAsset(fileName))
        val stepsArray = json.getJSONArray("steps")
        val steps = (0 until stepsArray.length()).map { i ->
            val s = stepsArray.getJSONObject(i)
            LessonStep(
                id = s.getString("id"),
                order = s.getInt("order"),
                title = s.getString("title"),
                shortExplanation = s.getString("shortExplanation"),
                spokenText = s.optString("spokenText", null),
                imageAsset = s.optString("imageAsset", null),
                audioAsset = s.optString("audioAsset", null)
            )
        }.sortedBy { it.order }
        return Lesson(
            id = json.getString("id"),
            title = json.getString("title"),
            description = json.optString("description", ""),
            steps = steps
        )
    }

    fun loadFatiha(): List<AyahItem> {
        val json = JSONObject(readAsset("fatiha.json"))
        val arr = json.getJSONArray("ayahs")
        return (0 until arr.length()).map { i ->
            val a = arr.getJSONObject(i)
            AyahItem(
                order = a.getInt("order"),
                arabicText = a.getString("arabicText"),
                audioAsset = a.optString("audioAsset", null)
            )
        }.sortedBy { it.order }
    }

    fun loadAdhkar(): List<DhikrItem> {
        val arr = JSONArray(readAsset("adhkar.json"))
        return (0 until arr.length()).map { i ->
            val d = arr.getJSONObject(i)
            DhikrItem(
                id = d.getString("id"),
                relatedStage = d.optString("relatedStage", ""),
                arabicText = d.getString("arabicText"),
                meaningForChildren = d.optString("meaningForChildren", null),
                audioAsset = d.optString("audioAsset", null)
            )
        }
    }

    fun loadQuiz(): List<QuizQuestion> {
        val arr = JSONArray(readAsset("quiz.json"))
        return (0 until arr.length()).map { i ->
            val q = arr.getJSONObject(i)
            val optionsArr = q.optJSONArray("options")
            val options = optionsArr?.let { oa -> (0 until oa.length()).map { oa.getString(it) } } ?: emptyList()
            val orderArr = q.optJSONArray("correctOrder")
            val correctOrder = orderArr?.let { oa -> (0 until oa.length()).map { oa.getInt(it) } }
            QuizQuestion(
                id = q.getString("id"),
                type = QuizType.valueOf(q.getString("type")),
                question = q.getString("question"),
                options = options,
                correctAnswerIndex = q.optInt("correctAnswerIndex", -1),
                correctOrder = correctOrder,
                explanation = q.optString("explanation", null)
            )
        }
    }

    fun loadFaq(): List<FaqEntry> {
        val arr = JSONArray(readAsset("faq.json"))
        return (0 until arr.length()).map { i ->
            val f = arr.getJSONObject(i)
            val kwArr = f.getJSONArray("keywords")
            val keywords = (0 until kwArr.length()).map { kwArr.getString(it) }
            FaqEntry(
                id = f.getString("id"),
                keywords = keywords,
                question = f.getString("question"),
                answer = f.getString("answer")
            )
        }
    }
}
