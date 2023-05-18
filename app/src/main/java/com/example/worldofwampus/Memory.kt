package com.example.worldofwampus

class Memory {
    val bloackBoard = arrayListOf<Memory.BloackBoard>()

    init {
        for (i in 0..15){
            when (i) {
                12 -> {
                    bloackBoard.add(BloackBoard(beenHere = true, isLocked = false))
                }
                13, 8 -> {
                    bloackBoard.add(BloackBoard(isLocked = false))
                }
                else -> {
                    bloackBoard.add(BloackBoard())
                }
            }
        }
    }

    inner class BloackBoard(
        var isLocked: Boolean = true,
        var beenHere: Boolean = false
    )

}