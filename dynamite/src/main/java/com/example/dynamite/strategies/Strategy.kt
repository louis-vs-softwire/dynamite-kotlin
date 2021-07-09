package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.softwire.dynamite.game.Move

interface Strategy {
    fun chooseMove(gameAnalysis: GameAnalysis): Move
}