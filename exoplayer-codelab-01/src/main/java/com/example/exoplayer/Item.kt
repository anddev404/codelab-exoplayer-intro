package com.example.exoplayer

class Item(val name: String = "", val timeInSec: Int, val type: ItemType) {

    companion object {
        fun getExampleItem(): List<Item> {
            return arrayListOf<Item>(
                Item("przerwa", 15, Break(R.raw.trzy)),
                Item("jeden", 40, Workout()),
                Item("przerwa", 15, Break(R.raw.dwa)),
                Item("2", 30, Workout()),
                Item("przerwa", 15, Break(R.raw.trzy)),
                Item("3", 35, Workout()),
                Item("przerwa", 5, Break(R.raw.cztery)),
                Item("4", 3, Workout()),
                Item("przerwa", 5, Break(R.raw.piec)),
                Item("5", 3, Workout()),
                Item("przerwa", 5, Break(R.raw.dwa)),
                Item("6", 3, Workout()),
                Item("przerwa", 5, Break(R.raw.trzy)),
                Item("7", 3, Workout()),
                Item("przerwa", 5, Break(R.raw.cztery)),
                Item("8 end", 3, Workout()),

                )
        }
    }
}