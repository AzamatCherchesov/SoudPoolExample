package com.a1tt.soundpool

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import java.io.IOException
import java.util.ArrayList

class BeatBox(context: Context) {
    private var assetManager: AssetManager = context.assets
    private val sounds = ArrayList<Sound>()
    private var soundPool: SoundPool

    fun getSounds(): List<Sound> {
        return sounds
    }

    companion object {
        private const val MAX_SOUNDS = 5
    }

    init {
        var soundsList: Array<String>? = null
        try {
            soundsList = assetManager.list("sample_sounds")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        soundPool = SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0)

        soundsList?.let {
            for (filePath in it) {
                val sound = Sound(filePath)
                sounds.add(sound)
                try {
                    load(sound)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    @Throws(IOException::class)
    private fun load(sound: Sound) {
        val afd = assetManager.openFd(sound.getAssetPath())
        val soundId = soundPool.load(afd, 1)
        sound.setSoundId(soundId)
    }

    fun play(sound: Sound) {
        val soundId = sound.getSoundId() ?: return
        soundPool.play(soundId, 1.0f, 1.0f, 1, 1, 1f)
    }
}