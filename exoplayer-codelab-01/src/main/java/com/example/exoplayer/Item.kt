package com.example.exoplayer

class Item(val name: String = "", val timeInSec: Int, val type: ItemType) {

    companion object {

        private val sets = CircularCollection(
            arrayListOf(
                Pair(getExampleItem(), "pierwszy zestaw"),
                Pair(getExampleItem2(), "drugi zestaw"),
                Pair(
                    getExampleItem(), "trzeci zestaw"
                )

            )
        )

        fun getAllSets(): CircularCollection<Pair<List<Item>, String>> {

            return sets
        }

        private fun getExampleItem(): List<Item> {
            return arrayListOf<Item>(
                Item("przerwa", 15, Break(R.raw.trzy)),
                Item("jeden", 40, Workout()),
                Item("przerwa", 15, Break(R.raw.dwa)),
                Item("2", 30, Workout()),
                Item("przerwa", 15, Break(R.raw.trzy)),
                Item("3", 35, Workout()),
                Item("przerwa", 5, Break(R.raw.cztery)),
                Item("4", 13, Workout()),
                Item("przerwa", 5, Break(R.raw.piec)),
                Item("5", 14, Workout()),
                Item("przerwa", 5, Break(R.raw.dwa)),
                Item("6", 15, Workout()),
                Item("przerwa", 5, Break(R.raw.trzy)),
                Item("7", 16, Workout()),
                Item("przerwa", 5, Break(R.raw.cztery)),
                Item("8 end", 3, Workout()),

                )
        }
        private fun getExampleItem2(): List<Item> {
            return arrayListOf<Item>(
                Item("przerwa", 5, Break(R.raw.trzy)),
                Item("koniec", 40, Workout()),


                )
        }
    }
}