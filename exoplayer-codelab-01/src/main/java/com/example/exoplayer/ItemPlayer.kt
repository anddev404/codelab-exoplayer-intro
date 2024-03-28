package com.example.exoplayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class ItemPlayer(
    private val items: List<Item> = Item.getExampleItem(),
    private val playerInterface: PlayerInterface
) {
    private var isPaused = false
    private var isStarted = false
    private var timer = 0

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    //   val mutex = Mutex(locked = false)

    fun start() {
        if (isStarted) return
        isStarted = true
        coroutineScope.launch() {
            playerInterface.startPlayer()
            for (item in items) {

                playerInterface.nextItem(item)


                for (x in 0..item.timeInSec) {
                    playerInterface.second(item.timeInSec - x, item.timeInSec, timer)
                    timer++
                    while (isPaused) {

                    }
                    delay(1000)
                }
            }
            isStarted = false
            playerInterface.endPlayer()

        }
    }

    fun pause() {
        isPaused = true
        playerInterface.pause()
    }

    fun resume() {
        isPaused = false
        playerInterface.resume()
    }
}