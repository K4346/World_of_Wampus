package com.example.worldofwampus

class Memory {
    val blackBoard = arrayOfNulls<Memory.BloackBoard>(16)
    var memoryMap: Array<Room?> = arrayOfNulls<Room?>(16)

    init {
        for (i in 0..15){
            when (i) {
                12 -> {
                    blackBoard[i]=(BloackBoard(beenHere = true, isLocked = false))
                }
                13, 8 -> {
                    blackBoard[i]=(BloackBoard(isLocked = false))
                }
                else -> {
                    blackBoard[i]=(BloackBoard())
                }
            }
        }
    }

    inner class BloackBoard(
        var isLocked: Boolean = true,
        var beenHere: Boolean = false
    )

}