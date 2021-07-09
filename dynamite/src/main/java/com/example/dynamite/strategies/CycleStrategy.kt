package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.softwire.dynamite.game.Move

class CycleStrategy : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        val moves = Move.values()
        val index = moves.indexOf(gameAnalysis.myLastMove()) + 1
        var move = moves.getOrElse(index) { moves[0] }
        if (move == Move.D && gameAnalysis.myDynamiteCount() >= 100) {
            move = Move.W
        }
        return move
    }
}