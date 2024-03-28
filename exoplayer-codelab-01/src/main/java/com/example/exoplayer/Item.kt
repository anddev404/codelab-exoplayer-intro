package com.example.exoplayer

class Item(val name: String = "", val timeInSec: Int, val type: ItemType) {

    companion object {
        fun getExampleItem(): List<Item> {
            return arrayListOf<Item>(
                Item("jeden", 5, Break(R.raw.trzy)),
                Item("przerwa", 3, Workout()),
                Item("dwa", 5, Break(R.raw.dwa)),
                Item("przerwa", 3, Workout()),
                Item("trzy", 5, Break(R.raw.trzy)),
                Item("przerwa", 3, Workout()),
                Item("cztery", 5, Break(R.raw.cztery)),
                Item("przerwa", 3, Workout()),
                Item("piec", 5, Break(R.raw.piec)),
                Item("jeden", 5, Break(R.raw.trzy)),
                Item("przerwa", 3, Workout()),
                Item("dwa", 5, Break(R.raw.dwa)),
                Item("przerwa", 3, Workout()),
                Item("trzy", 5, Break(R.raw.trzy)),
                Item("przerwa", 3, Workout()),
                Item("cztery", 5, Break(R.raw.cztery)),
                Item("przerwa", 3, Workout()),
                Item("piec", 5, Break(R.raw.piec)),

                )
        }
    }
}