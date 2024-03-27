package com.example.exoplayer

class Item(val timeInSec: Int) {//dodac scale dla wideo

    companion object {
        fun getExampleItem(): List<Item> {
            return arrayListOf<Item>(Item(10), Item(10), Item(11), Item(7))
        }
    }
}