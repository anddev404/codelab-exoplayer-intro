package com.example.exoplayer

sealed class ItemType

class Workout() : ItemType() {

}

class Break(val resource: Int) : ItemType() {

}

