package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.example.dynamite.helpers.getCounter
import com.softwire.dynamite.game.Move

class CounterStrategy : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        return getCounter(gameAnalysis.theirLastMove() ?: Move.D)
    }
}