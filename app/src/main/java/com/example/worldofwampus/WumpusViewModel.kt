package com.example.worldofwampus

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WumpusViewModel(application: Application) : AndroidViewModel(application) {

    private var memory = Memory()
    var memoryMap: Array<Room?> = arrayOfNulls<Room?>(16)

    val playerIndexMLD = MutableLiveData<Int>()
    val bestWayFoundedMLD = MutableLiveData<Pair<Int,Action>>()

    val gameOverMLD = MutableLiveData<Player.GameEnd>()

    var isBlockingTurn= false
    var isGameEnded=false

    init {
        memoryMap[12] = Room(false, false, false, false, false, false)
    }

    fun refresh(){
        memory = Memory()
        memoryMap = arrayOfNulls<Room?>(16)

    }

    fun makeTurn() {
        isBlockingTurn=true
        findBestWay()
    }

    private fun findBestWay() {

        val imagineWorlds = arrayListOf<ArrayList<Room>>()

        viewModelScope.launch(Dispatchers.Default) {

        while (imagineWorlds.size != 15) {
                val worldCandidate = Room.imageRoom()
                var k = 0
                memoryMap.forEachIndexed { index, room ->
                    if (room != null && room == worldCandidate[index]) {
                        k += 1
                    }
                }
                if (k == memoryMap.count { it != null }) {
                    imagineWorlds.add(worldCandidate)
                    Log.i("kpop",imagineWorlds.size.toString())
                }
            }
            val bestWay = DoubleArray(16)
            val wumpusPosition = imagineWorlds[0].indexOfFirst { it.wumpus==true }
            if (imagineWorlds.all { it[wumpusPosition].wumpus==true }
                && !memory.bloackBoard[wumpusPosition].isLocked && !memory.bloackBoard[wumpusPosition].beenHere){
                bestWayFoundedMLD.postValue(Pair(wumpusPosition,Action.KillWumpus))
            } else{
                imagineWorlds.forEach {
                    it.forEachIndexed { index, room ->
                        if (!memory.bloackBoard[index].isLocked && !memory.bloackBoard[index].beenHere){
                            if (room.hole==false && room.wumpus==false) {
                                bestWay[index] += 1.0
                                if (room.gold==true){
                                    bestWay[index] += 0.01
                                }
                            }
                        }
                    }
                }
                Log.i("kpop",bestWay.joinToString { it.toString() })
                bestWayFoundedMLD.postValue(Pair(bestWay.indices.maxBy { bestWay[it] },Action.Go))
            }

        }
    }

    fun goTo(map: ArrayList<Room>, newPlayerIndex: Int) {
        val currentRoom = map[newPlayerIndex]

        if (currentRoom.wumpus==true) {
            gameOverMLD.value=Player.GameEnd.WumpusEat
        } else if (currentRoom.hole==true) {
            gameOverMLD.value=Player.GameEnd.FallToHole
        } else if (currentRoom.gold==true) {
            gameOverMLD.value=Player.GameEnd.GetGold
        }

        playerIndexMLD.value=newPlayerIndex
        memoryMap[newPlayerIndex] = currentRoom

        memory.bloackBoard[newPlayerIndex].beenHere = true
        val up = if (newPlayerIndex - 4 >= 0) newPlayerIndex - 4 else -1
        val down = if (newPlayerIndex + 4 < 16) newPlayerIndex + 4 else -1
        val left = if (newPlayerIndex % 4 != 0) newPlayerIndex - 1 else -1
        val right = if (newPlayerIndex % 4 != 3) newPlayerIndex + 1 else -1


//        unlock
        if (up != -1) memory.bloackBoard[up].isLocked = false
        if (down != -1) memory.bloackBoard[down].isLocked = false
        if (left != -1) memory.bloackBoard[left].isLocked = false
        if (right != -1) memory.bloackBoard[right].isLocked = false
    }

    fun killWumpus(map: ArrayList<Room>, newPlayerIndex: Int) {
        val currentRoom = map[newPlayerIndex]

        if (currentRoom.wumpus==true) {
            gameOverMLD.value=Player.GameEnd.WumpusKill
            map[newPlayerIndex].wumpus=false
            map.forEach { it.smell=false }
            memoryMap.forEach {
                it?.wumpus=false
                it?.smell= false
            }
        } else{
            gameOverMLD.value=Player.GameEnd.ArrowNo
        }
    }

    enum class Action{
        Go, KillWumpus
    }

}