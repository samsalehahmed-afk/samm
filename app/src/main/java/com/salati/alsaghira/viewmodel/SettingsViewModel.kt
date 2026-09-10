package com.salati.alsaghira.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.salati.alsaghira.domain.AppAudioManager

class SettingsViewModel(private val audioManager: AppAudioManager) : ViewModel() {

    var soundEnabled by mutableStateOf(true)
        private set

    var volume by mutableStateOf(1.0f)
        private set

    fun toggleSound(enabled: Boolean) {
        soundEnabled = enabled
        audioManager.setMuted(!enabled)
    }

    fun setVolume(newVolume: Float) {
        volume = newVolume
        audioManager.setVolume(newVolume)
    }
}
