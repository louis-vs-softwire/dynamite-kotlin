package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.softwire.dynamite.game.Move

class NoStrategy : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        return Move.P
    }
}