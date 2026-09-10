package com.salati.alsaghira.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salati.alsaghira.data.local.ProgressStore
import com.salati.alsaghira.data.repository.ContentRepository
import com.salati.alsaghira.domain.AppAudioManager
import com.salati.alsaghira.domain.AssistantEngine

/**
 * Simple manual dependency injection. The app is small enough that a DI
 * framework (Hilt/Koin) would add more complexity than value for the MVP —
 * this factory is the single place that wires ViewModels to their dependencies,
 * which keeps it easy to swap in Hilt later without touching UI code.
 */
class AppViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val appContext = context.applicationContext
    private val contentRepository = ContentRepository(appContext)
    private val progressStore = ProgressStore(appContext)
    private val audioManager = AppAudioManager(appContext)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProgressViewModel::class.java) ->
                ProgressViewModel(progressStore) as T

            modelClass.isAssignableFrom(WuduViewModel::class.java) ->
                WuduViewModel(contentRepository, audioManager) as T

            modelClass.isAssignableFrom(PrayerViewModel::class.java) ->
                PrayerViewModel(contentRepository, audioManager) as T

            modelClass.isAssignableFrom(QuizViewModel::class.java) ->
                QuizViewModel(contentRepository) as T

            modelClass.isAssignableFrom(AssistantViewModel::class.java) ->
                AssistantViewModel(contentRepository) as T

            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(audioManager) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
