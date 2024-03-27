/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.exoplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.exoplayer.databinding.ActivityPlayerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UnstableApi
/**
 * A fullscreen activity to play audio or video streams.
 */
class PlayerActivity : AppCompatActivity(), PlayerInterface {

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    private var player: Player? = null
    private var activity: Activity = this
    val i = ItemPlayer(playerInterface = this)

    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L
    private lateinit var time: TextView
    private lateinit var time_all: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private var timerIsWorking = false
    private var timer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        time = findViewById(R.id.time)
        time_all = findViewById(R.id.time_all)
        startButton = findViewById(R.id.start)
        pauseButton = findViewById(R.id.pause)
        startButton.setOnClickListener {
            start()
        }
        pauseButton.setOnClickListener {
            if (timerIsWorking) {
                i.pause()
                timerIsWorking = false
                pauseButton.text = "RESUME"
            } else {
                i.resume()
                timerIsWorking = true
                pauseButton.text = "PAUSE"

            }

        }
        Log.d("MARCIN_W", "START");
    }

    fun start() {
        i.start()
        timerIsWorking = true
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                delay(1000)
                if (timerIsWorking) {
                    timer++
                    time_all.text = "" + timer
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        Log.d("MARCIN_W", "resumeeee 2222222");

        hideSystemUi()
        Log.d("MARCIN_W", "resumeeee 33333333");

        if (Build.VERSION.SDK_INT <= 23 || player == null) {
            initializePlayer()
            Log.d("MARCIN_W", "resumeeee 444444");

        }
        Log.d("MARCIN_W", "resumeeee 55555555");

        i.resume()
        Log.d("MARCIN_W", "resumeeee 666666666");

        timerIsWorking = true
        Log.d("MARCIN_W", "resumeeee 111111");

    }

    public override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer()
        }
        i.pause()
        timerIsWorking = false
    }

    public override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        // ExoPlayer implements the Player interface
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            viewBinding.videoView.player = exoPlayer

            var qwe1 = RawResourceDataSource.buildRawResourceUri(R.raw.youtube)
            var qwe2 = RawResourceDataSource.buildRawResourceUri(R.raw.internet)

            exoPlayer.videoScalingMode
            //  val mediaItem = MediaItem.fromUri(pattt)
            val mediaItem1 = MediaItem.fromUri(qwe1)
            val mediaItem2 = MediaItem.fromUri(qwe2)

//                val basePath = Environment.getExternalStorageDirectory().absolutePath
//                val filePath = "file://$basePath/workout/internet.mp4"
//                val mediaItem = MediaItem.fromUri(filePath)
            exoPlayer.setMediaItems(
                listOf(mediaItem1, mediaItem2), mediaItemIndex, playbackPosition
            )
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
//                exoPlayer.playWhenReady = playWhenReady
            exoPlayer.prepare()
        }
    }

    private fun releasePlayer() {
        player?.let { player ->
            playbackPosition = player.currentPosition
            mediaItemIndex = player.currentMediaItemIndex
            playWhenReady = player.playWhenReady
            player.release()
        }
        player = null
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun startPlayer() {
        Log.d("MARCIN_W", "start player");
    }

    override fun nextItem(item: Item) {
        Log.d("MARCIN_W", "start item");
    }

    override fun endPlayer() {
        Log.d("MARCIN_W", "end player");
    }

    override fun second(seconds: Int) {
        Log.d("MARCIN_W", "second: $seconds");
        time.text = "" + seconds
    }

    override fun pause() {
        Log.d("MARCIN_W", "paused");
    }

    override fun resume() {
        Log.d("MARCIN_W", "resumed");
    }
}