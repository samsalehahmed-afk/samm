package com.salati.alsaghira.domain

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

/**
 * Central place for playing all lesson/dhikr audio.
 *
 * Design notes:
 * - Reads audio files from assets/audio/ so lessons work fully offline.
 * - If the final audio file is not yet present (placeholder stage of the project),
 *   playback simply fails silently and is logged — it never crashes the lesson flow,
 *   and it never calls out to any external/network service.
 * - Only one clip plays at a time (starting a new one stops the previous).
 */
class AppAudioManager(private val context: Context) {

    private var player: MediaPlayer? = null
    private var isMuted: Boolean = false
    private var volume: Float = 1.0f

    fun setMuted(muted: Boolean) {
        isMuted = muted
        if (muted) stop()
    }

    fun setVolume(newVolume: Float) {
        volume = newVolume.coerceIn(0f, 1f)
        player?.setVolume(volume, volume)
    }

    /**
     * Plays an audio asset located at assets/audio/<assetPath>.
     * Safe to call even if the file does not exist yet (MVP placeholder stage).
     */
    fun play(assetPath: String?, onFinished: (() -> Unit)? = null) {
        if (isMuted || assetPath.isNullOrBlank()) {
            onFinished?.invoke()
            return
        }
        stop()
        try {
            val afd = context.assets.openFd("audio/$assetPath")
            player = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                setVolume(volume, volume)
                setOnCompletionListener {
                    onFinished?.invoke()
                    release()
                }
                prepare()
                start()
            }
            afd.close()
        } catch (e: Exception) {
            // Placeholder audio not yet added — fail silently, never crash the lesson.
            Log.w("AppAudioManager", "Audio asset not available yet: $assetPath")
            onFinished?.invoke()
        }
    }

    fun stop() {
        player?.let {
            try {
                if (it.isPlaying) it.stop()
                it.release()
            } catch (_: Exception) {
            }
        }
        player = null
    }
}
