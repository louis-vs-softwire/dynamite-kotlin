package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.softwire.dynamite.game.Move
import kotlin.random.Random

class RandomStrategy : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        return randomMove(gameAnalysis.myDynamiteCount() < 100, gameAnalysis.theirDynamiteCount() < 100)
    }

    private fun randomMove(canUseDynamite: Boolean, theyCanUseDynamite: Boolean): Move {
        return when (Random.Default.nextInt(1, 6)) {
            1 -> Move.R
            2 -> Move.P
            3 -> Move.S
            4 -> if (theyCanUseDynamite) Move.W else Move.R
            5 -> if (canUseDynamite) Move.D else Move.P
            else -> Move.S
        }
    }
}