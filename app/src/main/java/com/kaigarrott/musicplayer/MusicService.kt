package com.kaigarrott.musicplayer

import android.annotation.TargetApi
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import java.io.File
import java.io.IOException

class MusicService : Service(),
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnSeekCompleteListener,
    AudioManager.OnAudioFocusChangeListener {

    private val binder = LocalBinder()
    private lateinit var player: MediaPlayer
    private var currentTrack: String? = null
    private var position: Int = 0

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        togglePlay()
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCompletion(mp: MediaPlayer?) {
        stop()
        stopSelf()
    }

    override fun onAudioFocusChange(focusChange: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @TargetApi(21)
    private fun initPlayer() {
        player = MediaPlayer()
        player.setOnPreparedListener(this)
        player.setOnSeekCompleteListener(this)
        player.setOnCompletionListener(this)
        player.reset()
        if(BuildConfig.VERSION_CODE >= Build.VERSION_CODES.LOLLIPOP) {
            val attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            player.setAudioAttributes(attributes)
        } else {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        try {
            player.setDataSource(currentTrack)
        } catch(e: IOException) {
            e.printStackTrace()
            stopSelf()
        }

    }

    private fun togglePlay() {
        if(player.isPlaying) {
            player.pause()
            position = player.currentPosition
            return
        }
        if(position > 0) {
            player.seekTo(position)
            position = 0
        }
        player.start()
    }

    private fun stop() {
        if(player.isPlaying) player.stop()
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }
}
