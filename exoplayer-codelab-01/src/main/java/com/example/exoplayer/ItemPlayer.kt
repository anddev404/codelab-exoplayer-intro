package com.example.exoplayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemPlayer(
    private var items: Pair<List<Item>, String> = Item.getAllSets().getNext(),
    private val playerInterface: PlayerInterface
) {
    private var isPaused = false
    private var isStarted = false
    private var timer = 0
    var description = items.second

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    //   val mutex = Mutex(locked = false)
    fun rightSet() {
        val item = Item.getAllSets().getNext()
        items = item
        description = item.second
    }

    fun leftSet() {
        val item = Item.getAllSets().getPrevious()
        items = item
        description = item.second
    }

    fun start() {
        if (isStarted) return
        isStarted = true
        coroutineScope.launch() {
            playerInterface.startPlayer()
            for (item in items.first) {

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