package com.a1tt.soundpool

class Sound(assetPath: String) {
    private var assetPath: String = "sample_sounds/$assetPath"
    private var soundName: String
    private var soundId: Int = 0

    init {
        val components =
            assetPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        soundName = components[components.size - 1].replace(".wav", "")
    }

    fun getSoundId(): Int? {
        return soundId
    }

    fun setSoundId(soundId: Int) {
        this.soundId = soundId
    }

    fun getAssetPath(): String {
        return assetPath
    }

    fun getName(): String {
        return soundName
    }
}
