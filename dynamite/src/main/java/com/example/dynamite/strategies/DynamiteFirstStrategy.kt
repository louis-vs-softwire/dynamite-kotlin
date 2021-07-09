package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.example.dynamite.helpers.RoundOutcome
import com.example.dynamite.helpers.randomMove
import com.example.dynamite.helpers.roundOutcome
import com.softwire.dynamite.game.Move
import kotlin.random.Random

class DynamiteFirstStrategy : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        if (gameAnalysis.totalDraws(gameAnalysis.getLastXRounds(10)) > 7) {
            // looks like we're fighting fire with fire
            return Move.W
        }
        if (gameAnalysis.myDynamiteCount() < 100) return Move.D

        // default to random
        return randomMove(canUseDynamite = false, theyCanUseDynamite = true)
    }
}