package com.example.worldofwampus


data class Room(
    var wumpus: Boolean,
    var smell: Boolean,
    val hole: Boolean,
    var wind: Boolean,
    var gold: Boolean,
    var shine: Boolean
) {
    companion object {
        fun imageRoom(): ArrayList<Room> {
            val list = arrayListOf<ArrayList<Room>>()
            var line: ArrayList<Room>
            var wumpus = true
            var gold = true
            for (i in 0..3) {
                line = arrayListOf()
                for (j in 0..3) {
                    if ((i == 3 && j == 0) || (i == 2 && j == 0) || (i == 3 && j == 1)) {
                        line.add(
                            Room(
                                wumpus = false,
                                smell = false,
                                hole = false,
                                wind = false,
                                gold = false,
                                shine = false
                            )
                        )
                        continue
                    } else if ((i == 3 && j == 3)) {
                        line.add(
                            Room(
                                wumpus = wumpus,
                                smell = false,
                                hole = false,
                                wind = false,
                                gold = gold,
                                shine = false
                            )
                        )
                        continue
                    } else {
                        val wumpusChance = (0..12).random() == 0
                        val goldChance = (0..12).random() == 0
                        val hole = (1..5).random() == 1
                        line.add(
                            Room(
                                wumpus = wumpusChance && wumpus,
                                smell = false,
                                hole = hole,
                                wind = false,
                                goldChance && gold,
                                false
                            )
                        )
                        if (wumpusChance) wumpus = false
                        if (goldChance) gold = false
                    }
                }
                list.add(line)
            }

            return initWindAndShine(list).flatten() as ArrayList<Room>
        }

        private fun initWindAndShine(list: List<List<Room>>): List<List<Room>> {
            for (i in 0..3) {
                for (j in 0..3) {
                    if (list[i][j].hole == true) {
                        if (i > 0) {
                            list[i - 1][j].wind = true
                        }
                        if (i < list.size - 1) {
                            list[i + 1][j].wind = true
                        }
                        if (j > 0) {
                            list[i][j - 1].wind = true
                        }
                        if (j < list[0].size - 1) {
                            list[i][j + 1].wind = true
                        }
                    }
                    if (list[i][j].gold == true) {
                        if (i > 0) {
                            list[i - 1][j].shine = true
                        }
                        if (i < list.size - 1) {
                            list[i + 1][j].shine = true
                        }
                        if (j > 0) {
                            list[i][j - 1].shine = true
                        }
                        if (j < list[0].size - 1) {
                            list[i][j + 1].shine = true
                        }
                    }
                    if (list[i][j].wumpus == true) {
                        if (i > 0) {
                            list[i - 1][j].smell = true
                        }
                        if (i < list.size - 1) {
                            list[i + 1][j].smell = true
                        }
                        if (j > 0) {
                            list[i][j - 1].smell = true
                        }
                        if (j < list[0].size - 1) {
                            list[i][j + 1].smell = true
                        }
                    }
                }
            }
            return list
        }
    }
}
