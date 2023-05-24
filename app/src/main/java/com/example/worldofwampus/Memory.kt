package com.example.worldofwampus

class Memory {
    val blackBoard = arrayListOf<Memory.BloackBoard>()
    var memoryMap: Array<Room?> = arrayOfNulls<Room?>(16)

    init {
        for (i in 0..15){
            when (i) {
                12 -> {
                    blackBoard.add(BloackBoard(beenHere = true, isLocked = false))
                }
                13, 8 -> {
                    blackBoard.add(BloackBoard(isLocked = false))
                }
                else -> {
                    blackBoard.add(BloackBoard())
                }
            }
        }
    }

    inner class BloackBoard(
        var isLocked: Boolean = true,
        var beenHere: Boolean = false
    )

}