package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.example.dynamite.helpers.randomMove
import com.softwire.dynamite.game.Move
import kotlin.random.Random

class RandomStrategy : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        return randomMove(gameAnalysis.myDynamiteCount() < 100, gameAnalysis.theirDynamiteCount() < 100)
    }
}