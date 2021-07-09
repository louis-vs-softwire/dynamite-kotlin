package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.softwire.dynamite.game.Move

class MonoStrategy(val move: Move) : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        return move
    }
}