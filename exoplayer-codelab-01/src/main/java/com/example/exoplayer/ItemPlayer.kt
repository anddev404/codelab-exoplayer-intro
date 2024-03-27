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
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    //   val mutex = Mutex(locked = false)

    fun start() {
        coroutineScope.launch() {
            playerInterface.startPlayer()
            for (item in items) {
                playerInterface.nextItem(item)
                for (i in 0..item.timeInSec) {

                    playerInterface.second(i)
                    while (isPaused) {

                    }
                    delay(1000)
                }
            }
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