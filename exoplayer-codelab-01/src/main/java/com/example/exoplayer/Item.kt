package com.example.exoplayer

class Item(val name: String = "", val timeInSec: Int, resource: Int? = null) {

    companion object {
        fun getExampleItem(): List<Item> {
            return arrayListOf<Item>(
                Item("jeden", 10),
                Item("dwa", 10),
                Item("trzy", 11),
                Item("cztery", 7)
            )
        }
    }
}