package com.example.worldofwampus

class Player {


    enum class GameEnd(val title: String) {
        FallToHole("Игрок упал в яму"),
        GetGold("Игрок получил золото"),
        WumpusEat("Игрока съел Вампус"),
        WumpusKill("Игрок выстрелил стрелой и убил Вампуса"),
        ArrowNo("Игрок потратил стрелу и не попал"),
        NoTurns("Некуда идти")
    }
}