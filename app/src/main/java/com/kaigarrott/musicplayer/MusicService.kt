package com.kaigarrott.musicplayer

import android.annotation.TargetApi
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
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
    private lateinit var attributes: AudioAttributes
    private lateinit var player: MediaPlayer
    private lateinit var manager: AudioManager
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
        when(focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                if(!this::player.isInitialized) initPlayer()
                if(!player.isPlaying) player.start()
                player.setVolume(1.0f, 1.0f)
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                if(player.isPlaying) player.stop()
                player.release()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                if(player.isPlaying) togglePlay()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                if(player.isPlaying) player.setVolume(0.1f, 0.1f)
            }
        }
    }

    @TargetApi(26)
    private fun requestFocus(): Boolean {
        manager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val check: Int
        if(BuildConfig.VERSION_CODE >= Build.VERSION_CODES.O) {
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(attributes)
                .setAcceptsDelayedFocusGain(true)
                .setWillPauseWhenDucked(false)
                .setOnAudioFocusChangeListener(this)
                .build()
            check = manager.requestAudioFocus(request)
        } else {
            check = manager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
        if(check == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            return true
        }
        return false
    }

    @TargetApi(26)
    private fun abandonFocus(): Boolean {
        if(BuildConfig.VERSION_CODE >= Build.VERSION_CODES.O) {
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(attributes)
                .setAcceptsDelayedFocusGain(true)
                .setWillPauseWhenDucked(false)
                .setOnAudioFocusChangeListener(this)
                .build()
            return manager.abandonAudioFocusRequest(request) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        } else {
            return manager.abandonAudioFocus(this) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

    @TargetApi(21)
    private fun initPlayer() {
        player = MediaPlayer()
        player.setOnPreparedListener(this)
        player.setOnSeekCompleteListener(this)
        player.setOnCompletionListener(this)
        player.reset()
        if(BuildConfig.VERSION_CODE >= Build.VERSION_CODES.LOLLIPOP) {
            attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
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
