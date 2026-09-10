package com.salati.alsaghira.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.salati.alsaghira.data.repository.ContentRepository
import com.salati.alsaghira.domain.AppAudioManager
import com.salati.alsaghira.model.AyahItem
import com.salati.alsaghira.model.DhikrItem
import com.salati.alsaghira.model.Lesson

class PrayerViewModel(
    repository: ContentRepository,
    private val audioManager: AppAudioManager
) : ViewModel() {

    val lesson: Lesson = repository.loadPrayerLesson()
    val fatihaAyahs: List<AyahItem> = repository.loadFatiha()
    val adhkar: List<DhikrItem> = repository.loadAdhkar()

    var currentStepIndex by mutableStateOf(0)
        private set

    val currentStep get() = lesson.steps.getOrNull(currentStepIndex)
    val isFirstStep get() = currentStepIndex == 0
    val isLastStep get() = currentStepIndex == lesson.steps.lastIndex

    var currentAyahIndex by mutableStateOf(0)
        private set

    fun goNextStep() {
        if (!isLastStep) currentStepIndex++
    }

    fun goPreviousStep() {
        if (!isFirstStep) currentStepIndex--
    }

    fun goNextAyah() {
        if (currentAyahIndex < fatihaAyahs.lastIndex) currentAyahIndex++
    }

    fun goPreviousAyah() {
        if (currentAyahIndex > 0) currentAyahIndex--
    }

    fun playCurrentStepAudio() {
        audioManager.play(currentStep?.audioAsset)
    }

    fun playAyah(index: Int) {
        audioManager.play(fatihaAyahs.getOrNull(index)?.audioAsset)
    }

    fun playDhikr(dhikr: DhikrItem) {
        audioManager.play(dhikr.audioAsset)
    }

    override fun onCleared() {
        audioManager.stop()
        super.onCleared()
    }
}
