package com.example.dynamite

import com.example.dynamite.helpers.RoundOutcome
import com.example.dynamite.helpers.getCounter
import com.example.dynamite.helpers.roundOutcome
import com.example.dynamite.strategies.*
import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move

class StrategyBot : Bot {
    private var currentStrategy: Strategy = NoStrategy()

    companion object : Factory<StrategyBot> {
        override fun create(): StrategyBot = StrategyBot()
    }

    init {
        println("Started new match")
    }

    override fun makeMove(gamestate: Gamestate): Move {
        // get analysis of current gamestate
        val analysis = GameAnalysis(gamestate)

        switchStrategy(analysis)

        return currentStrategy.chooseMove(analysis)
    }

    private fun switchStrategy(analysis: GameAnalysis) {
        val lastFiveRounds = analysis.getLastXRounds(5)
        val theirRecentMoves = analysis.theirMoves(lastFiveRounds)
        val theirRecentMostFrequentMove = analysis.mostFrequentMove(theirRecentMoves)
        val theirRecentMostFrequentMoveFrequency = analysis.calculateMoveFrequencies(theirRecentMoves)[theirRecentMostFrequentMove] ?: 0

        when (currentStrategy) {
            // always start random - feel out the enemy
            is NoStrategy -> currentStrategy = DynamiteFirstStrategy()
            is MonoStrategy -> {
                // if this isn't working, revert to random
                if (analysis.totalWins(analysis.getLastXRounds(20)) < 10) {
                    currentStrategy = RandomStrategy()
                }
            }
            is RandomStrategy -> {
                // try to detect patterns:

                // use mono strategy to counter mono bots
                if (theirRecentMostFrequentMoveFrequency > 4) {
                    currentStrategy = MonoStrategy(getCounter(theirRecentMostFrequentMove))
                }

                // boost
                if ((analysis.totalWins() > 500 || analysis.totalLosses() > 500) && analysis.myDynamiteCount() < 100) {
                    currentStrategy = DynamiteFirstStrategy()
                }
            }
            is CycleStrategy -> {
                // use mono strategy to counter mono bots
                if (theirRecentMostFrequentMoveFrequency > 5) {
                    currentStrategy = MonoStrategy(getCounter(theirRecentMostFrequentMove))

                }
            }
            is DynamiteFirstStrategy -> {
                if (analysis.myDynamiteCount() >= 100) {
                    currentStrategy = RandomStrategy()
                }

            }
        }
    }


}