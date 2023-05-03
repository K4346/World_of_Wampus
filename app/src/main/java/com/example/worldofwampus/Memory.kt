package com.example.worldofwampus

class Memory {
    val rooms = arrayListOf<Memory.Room>()

    init {
        for (i in 0..15){
            when (i) {
                12 -> {
                    rooms.add(Room(beenHere = true, isLocked = false, wumpus = wumpusState.False, hole = holeState.False, gold = goldState.False))
                }
                13, 8 -> {
                    rooms.add(Room(isLocked = false, wumpus = wumpusState.False, hole = holeState.False, gold = goldState.False))
                }
                else -> {
                    rooms.add(Room())
                }
            }
        }
    }

    inner class Room(
        var isLocked: Boolean = true,
        var beenHere: Boolean = false,
        var wumpus: wumpusState = wumpusState.Unknown,
        var hole: holeState = holeState.Unknown,
        var gold: goldState = goldState.Unknown,
    ){
        val risk
        get() = wumpus.risk+hole.risk+gold.risk
    }

    enum class wumpusState(val risk: Int) {
        True(1000),
        Possible(100),
        Mark(0),
        Unknown(10),
        False(0),
    }
    enum class holeState(val risk: Int) {
        True(100),
        Possible(10),
        Mark(0),
        Unknown(5),
        False(0),
    }
    enum class goldState(val risk: Int) {
        True(-60),
        Possible(-2),
        Mark(0),
        Unknown(-1),
        False(0),
    }
}