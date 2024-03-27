package com.example.exoplayer

interface PlayerInterface {

    fun startPlayer()
    fun nextItem(item: Item)
    fun endPlayer()

    fun second(seconds: Int, secondAll: Int, secondAllAll: Int)

    fun pause()
    fun resume()

}