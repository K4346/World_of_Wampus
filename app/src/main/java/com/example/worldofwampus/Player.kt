package com.example.worldofwampus

import android.content.Context
import android.util.Log
import android.widget.Toast

class Player {

    private val memory = Memory()

    fun makeTurn(map: ArrayList<Room>, context: Context) {
        val index = findBestWay()
        if (index==-1){
            gameOver(context, GameEnd.NoTurns)
        } else {
            goTo(map, index, context)
            markRooms()
        }
    }

    private fun goTo(map: ArrayList<Room>, newPlayerIndex: Int, context: Context) {
        if (memory.rooms.any { it.wumpus == Memory.wumpusState.True }) {
            Log.i("kpop wumpus kill",memory.rooms.indexOfFirst { it.wumpus==Memory.wumpusState.True }.toString())
            gameOver(context, GameEnd.WumpusKill)
        }

        map.forEach { it.player = false }
        map[newPlayerIndex].player = true

        if (map[newPlayerIndex].hole) gameOver(context, GameEnd.FallToHole)
        if (map[newPlayerIndex].wumpus) gameOver(context, GameEnd.WumpusEat)
        if (map[newPlayerIndex].gold) gameOver(context, GameEnd.GetGold)

        memory.rooms[newPlayerIndex].beenHere = true
        val up = if (newPlayerIndex - 4 >= 0) newPlayerIndex - 4 else -1
        val down = if (newPlayerIndex + 4 < 16) newPlayerIndex + 4 else -1
        val left = if (newPlayerIndex % 4 != 0) newPlayerIndex - 1 else -1
        val right = if (newPlayerIndex % 4 != 3) newPlayerIndex + 1 else -1
        val currentRoom = map[newPlayerIndex]

//        unlock
        if (up != -1) memory.rooms[up].isLocked = false
        if (down != -1) memory.rooms[down].isLocked = false
        if (left != -1) memory.rooms[left].isLocked = false
        if (right != -1) memory.rooms[right].isLocked = false

        if (currentRoom.smell) {
            memory.rooms[newPlayerIndex].wumpus= Memory.wumpusState.Mark
            if (up != -1 && !memory.rooms[up].beenHere  && memory.rooms[up].wumpus==Memory.wumpusState.Unknown) memory.rooms[up].wumpus = Memory.wumpusState.Possible
            if (down != -1 && !memory.rooms[down].beenHere  && memory.rooms[down].wumpus==Memory.wumpusState.Unknown) memory.rooms[down].wumpus = Memory.wumpusState.Possible
            if (left != -1 && !memory.rooms[left].beenHere  && memory.rooms[left].wumpus==Memory.wumpusState.Unknown) memory.rooms[left].wumpus = Memory.wumpusState.Possible
            if (right != -1 && !memory.rooms[right].beenHere  && memory.rooms[right].wumpus==Memory.wumpusState.Unknown) memory.rooms[right].wumpus = Memory.wumpusState.Possible
        } else {
            if (up != -1 && !memory.rooms[up].beenHere && memory.rooms[up].wumpus!=Memory.wumpusState.True) memory.rooms[up].wumpus = Memory.wumpusState.False
            if (down != -1 && !memory.rooms[down].beenHere && memory.rooms[down].wumpus!=Memory.wumpusState.True) memory.rooms[down].wumpus = Memory.wumpusState.False
            if (left != -1 && !memory.rooms[left].beenHere && memory.rooms[left].wumpus!=Memory.wumpusState.True) memory.rooms[left].wumpus = Memory.wumpusState.False
            if (right != -1 && !memory.rooms[right].beenHere &&  memory.rooms[right].wumpus!=Memory.wumpusState.True) memory.rooms[right].wumpus = Memory.wumpusState.False
        }

//        todo hole могут повторяться поэтому сейчас возможно что гг отмететит клетку в которой есть wind но нет hole
        if (currentRoom.wind) {
            memory.rooms[newPlayerIndex].hole= Memory.holeState.Mark
            if (up != -1 && !memory.rooms[up].beenHere && memory.rooms[up].hole==Memory.holeState.Unknown) memory.rooms[up].hole = Memory.holeState.Possible
            if (down != -1 && !memory.rooms[down].beenHere && memory.rooms[down].hole==Memory.holeState.Unknown) memory.rooms[down].hole = Memory.holeState.Possible
            if (left != -1  && !memory.rooms[left].beenHere && memory.rooms[left].hole==Memory.holeState.Unknown) memory.rooms[left].hole = Memory.holeState.Possible
            if (right != -1 && !memory.rooms[right].beenHere && memory.rooms[right].hole==Memory.holeState.Unknown) memory.rooms[right].hole = Memory.holeState.Possible
        } else {
            if (up != -1 && !memory.rooms[up].beenHere && memory.rooms[up].hole!=Memory.holeState.True) memory.rooms[up].hole = Memory.holeState.False
            if (down != -1 && !memory.rooms[down].beenHere && memory.rooms[down].hole!=Memory.holeState.True) memory.rooms[down].hole = Memory.holeState.False
            if (left != -1 && !memory.rooms[left].beenHere && memory.rooms[left].hole!=Memory.holeState.True)  memory.rooms[left].hole = Memory.holeState.False
            if (right != -1 && !memory.rooms[right].beenHere && memory.rooms[right].hole!=Memory.holeState.True) memory.rooms[right].hole = Memory.holeState.False
        }
// todo если золото получено то нужно убирать метки possible
        if (currentRoom.shine) {
            memory.rooms[newPlayerIndex].gold= Memory.goldState.Mark
            if (up != -1 && !memory.rooms[up].beenHere && memory.rooms[up].gold==Memory.goldState.Unknown) memory.rooms[up].gold = Memory.goldState.Possible
            if (down != -1 && !memory.rooms[down].beenHere && memory.rooms[down].gold==Memory.goldState.Unknown) memory.rooms[down].gold = Memory.goldState.Possible
            if (left != -1 && memory.rooms[left].gold==Memory.goldState.Unknown) memory.rooms[left].gold = Memory.goldState.Possible
            if (right != -1 && !memory.rooms[right].beenHere && memory.rooms[right].gold==Memory.goldState.Unknown) memory.rooms[right].gold = Memory.goldState.Possible
        } else {
            if (up != -1 && !memory.rooms[up].beenHere && memory.rooms[up].gold!=Memory.goldState.True) memory.rooms[up].gold = Memory.goldState.False
            if (down != -1 && !memory.rooms[down].beenHere && memory.rooms[down].gold!=Memory.goldState.True) memory.rooms[down].gold = Memory.goldState.False
            if (left != -1 && !memory.rooms[left].beenHere && memory.rooms[left].gold!=Memory.goldState.True) memory.rooms[left].gold = Memory.goldState.False
            if (right != -1 && !memory.rooms[right].beenHere && memory.rooms[right].gold!=Memory.goldState.True) memory.rooms[right].gold = Memory.goldState.False
        }
    }

    private fun gameOver(context: Context, gameEnd: GameEnd) {
        Toast.makeText(context, gameEnd.title, Toast.LENGTH_LONG).show()
    }

    private fun findBestWay(): Int {
        var i = -1
        var minRisk = 1000000
        memory.rooms.forEachIndexed { index, room ->
            if (!room.isLocked && !room.beenHere) Log.i(
                "kpop",
                "$index ${room.risk} W-${room.wumpus}  H-${room.hole}  G-${room.gold}"
            )
            if (!room.isLocked && !room.beenHere && room.wumpus!=Memory.wumpusState.True && room.hole!=Memory.holeState.True   && room.risk < minRisk ) {
                i = index
                minRisk = room.risk
            }
        }
//        val minRoom = memory.rooms.minBy { it.risk }
        Log.i("kpop", i.toString())
//        memory.rooms.forEach { Log.i("kpop", it.risk.toString()) }
//        return memory.rooms.indexOf(minRoom)
        return i
    }

    private fun markRooms() {
        memory.rooms.forEachIndexed { index, room ->
            var smellCount = 0
            var windCount = 0
            var shineCount = 0
            val up = if (index - 4 >= 0) index - 4 else -1
            val down = if (index + 4 < 16) index + 4 else -1
            val left = if (index % 4 != 0) index - 1 else -1
            val right = if (index % 4 != 3) index + 1 else -1
            val sides = listOf(up, down, left, right)
            sides.forEach {side->
                if (side != -1) {
                    //todo smell и possible это разные вещи!!!!
                    if (memory.rooms[side].wumpus==Memory.wumpusState.Mark) smellCount += 1
                    if (memory.rooms[side].hole==Memory.holeState.Mark) windCount += 1
                    if (memory.rooms[side].gold==Memory.goldState.Mark) shineCount += 1
                }
            }
            if (smellCount >= 2) memory.rooms[index].wumpus = Memory.wumpusState.True
            if (windCount >= 2) memory.rooms[index].hole = Memory.holeState.True
            if (shineCount >= 2) memory.rooms[index].gold = Memory.goldState.True
        }

    }

    enum class GameEnd(val title: String) {
        FallToHole("Игрок упал в яму"),
        GetGold("Игрок получил золото"),
        WumpusEat("Игрока съел Вампус"),
        WumpusKill("Игрок убил Вампуса"),
        NoTurns("Некуда идти")
    }
}