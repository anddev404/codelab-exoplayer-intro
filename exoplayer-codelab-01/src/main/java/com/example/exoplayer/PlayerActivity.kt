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
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.exoplayer.databinding.ActivityPlayerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@UnstableApi
/**
 * A fullscreen activity to play audio or video streams.
 */
class PlayerActivity : AppCompatActivity(), PlayerInterface {

//region variables


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
    private lateinit var header: TextView
    private lateinit var time_break: TextView

    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private var timerIsWorking = false
    private var timer = 0

    //endregion


    fun start() {
        timerIsWorking = true
        i.start()

    }

    //region onMetody
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        time = findViewById(R.id.time)
        time_all = findViewById(R.id.time_all)
        header = findViewById(R.id.header)
        time_break = findViewById(R.id.time_break)
        time_break.visibility = View.INVISIBLE

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

    public override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            //   initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        Log.d("MARCIN_W", "resumeeee 2222222");

        hideSystemUi()
        Log.d("MARCIN_W", "resumeeee 33333333");

        if (Build.VERSION.SDK_INT <= 23 || player == null) {
            //initializePlayer()
            Log.d("MARCIN_W", "resumeeee 444444");

        }
        Log.d("MARCIN_W", "resumeeee 55555555");

        // i.resume()
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

//endregion

    private fun playExoPlayer(raw: Int) {
        Log.d("MARCIN_W", "EXO PALAY: $raw");

        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            viewBinding.videoView.player = exoPlayer
            Log.d("MARCIN_W", "EXO PALAY: OLSO");

            var qwe1 = RawResourceDataSource.buildRawResourceUri(raw)
            val mediaItem1 = MediaItem.fromUri(qwe1)
            exoPlayer.setMediaItems(
                listOf(mediaItem1), mediaItemIndex, playbackPosition
            )
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
            exoPlayer.playWhenReady = playWhenReady
            exoPlayer.prepare()
        }
    }

//    private fun initializePlayer() {
//        // ExoPlayer implements the Player interface
//        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
//            viewBinding.videoView.player = exoPlayer
//
//            var qwe1 = RawResourceDataSource.buildRawResourceUri(R.raw.youtube)
//            var qwe2 = RawResourceDataSource.buildRawResourceUri(R.raw.internet)
//            var qwe3 = RawResourceDataSource.buildRawResourceUri(R.raw.youtube)
//            var qwe4 = RawResourceDataSource.buildRawResourceUri(R.raw.internet)
//            exoPlayer.videoScalingMode
//            //  val mediaItem = MediaItem.fromUri(pattt)
//            val mediaItem1 = MediaItem.fromUri(qwe1)
//            val mediaItem2 = MediaItem.fromUri(qwe2)
//            val mediaItem3 = MediaItem.fromUri(qwe3)
//            val mediaItem4 = MediaItem.fromUri(qwe4)
//            exoPlayer.setMediaItems(
//                listOf(mediaItem1, mediaItem2, mediaItem3, mediaItem4),
//                mediaItemIndex,
//                playbackPosition
//            )
//            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
//            // exoPlayer.playWhenReady = playWhenReady
//            exoPlayer.prepare()
//        }
//    }

    override fun nextItem(item: Item) {
        if (item.type is Break) {
            Log.d("MARCIN_W", "start item ${item.type.resource}");

        }
        header.text = item.name
//        if(item.type is Workout){działą
//            item.type.
//        }
        when (item.type) {//to nei jest comprehensiv tzn jak nie mam wszytrkich typów tu to nei zapyta i nei wymusi
            is Break -> {

                CoroutineScope(Dispatchers.Main).launch {
                    time_break.visibility = View.VISIBLE
                    releasePlayer()
                    playExoPlayer(item.type.resource)
                }
            }

            is Workout -> time_break.visibility = View.INVISIBLE

//            is Break -> {
//                nextItem?.let {
//                    if (nextItem.type is Workout) {
//                        CoroutineScope(Dispatchers.Main).launch {
//                            playExoPlayer(nextItem.type.resource)
//                        }
//                    }
//                }
//            }
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


    override fun endPlayer() {
        Log.d("MARCIN_W", "end player");
        i.pause()
        timerIsWorking = false
    }

    override fun second(seconds: Int, secondAll: Int, secondAllAll: Int) {
        Log.d("MARCIN_W", "second: $seconds / $secondAll");
        time.text =
            "" + "${secondsToMinutesAndSeconds(seconds)}"


        runBlocking(Dispatchers.Main) {
            time_break.text = "BREAK ${seconds}"

        }


        //   "BREAK $seconds"

        time_all.text = "" + secondsToMinutesAndSeconds(secondAllAll)

    }

    override fun pause() {
        Log.d("MARCIN_W", "paused");
        CoroutineScope(Dispatchers.Main).launch {
            player?.pause()
        }
    }

    override fun resume() {
        Log.d("MARCIN_W", "resumed");
        CoroutineScope(Dispatchers.Main).launch {
            player?.play()
        }
    }

    fun secondsToMinutesAndSeconds(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%d:%02d", minutes, remainingSeconds)
    }

    fun aaa(seconds: Int): String {

        return Integer.toString(seconds)
    }
}