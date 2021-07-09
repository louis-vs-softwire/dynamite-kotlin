package com.example.dynamite.strategies

import com.example.dynamite.GameAnalysis
import com.example.dynamite.helpers.randomMove
import com.softwire.dynamite.game.Move

class AntiPatternStrategy(private val interval: Int) : Strategy {
    override fun chooseMove(gameAnalysis: GameAnalysis): Move {
        // crawl to find last round
        var distance = 0
        while (distance <= gameAnalysis.totalRounds()) {
            distance += 1
        }
        if (distance == interval) {
            return Move.W
        }
        return randomMove(canUseDynamite = false, theyCanUseDynamite = true)
    }
}