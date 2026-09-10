package com.salati.alsaghira.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.salati.alsaghira.data.repository.ContentRepository
import com.salati.alsaghira.domain.AppAudioManager
import com.salati.alsaghira.model.Lesson

class WuduViewModel(
    repository: ContentRepository,
    private val audioManager: AppAudioManager
) : ViewModel() {

    val lesson: Lesson = repository.loadWuduLesson()

    var currentStepIndex by mutableStateOf(0)
        private set

    val currentStep get() = lesson.steps.getOrNull(currentStepIndex)
    val isFirstStep get() = currentStepIndex == 0
    val isLastStep get() = currentStepIndex == lesson.steps.lastIndex

    fun goNext() {
        if (!isLastStep) currentStepIndex++
    }

    fun goPrevious() {
        if (!isFirstStep) currentStepIndex--
    }

    fun playCurrentAudio() {
        audioManager.play(currentStep?.audioAsset)
    }

    override fun onCleared() {
        audioManager.stop()
        super.onCleared()
    }
}
