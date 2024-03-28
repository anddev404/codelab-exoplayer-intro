package com.example.exoplayer

import android.util.Log

class CircularCollection<T>(private val items: List<T>) {
    private var currentIndex = -1

    fun getNext(): T {

        currentIndex = (currentIndex + 1)
        Log.d("MARCIN_W", "HEJ $currentIndex");

        if (currentIndex >= items.size) {
            currentIndex = 0
        }
        Log.d("MARCIN_W", "HEJ::: $currentIndex");

        return items[currentIndex]
    }

    fun getPrevious(): T {
        currentIndex = (currentIndex - 1 + items.size) % items.size
        return items[currentIndex]
    }
}