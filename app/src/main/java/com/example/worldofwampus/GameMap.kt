package com.example.worldofwampus


data class Room(
    var player: Boolean,
    val wumpus: Boolean,
    var smell: Boolean,
    val hole: Boolean,
    var wind: Boolean,
    var gold: Boolean,
    var shine: Boolean
)
