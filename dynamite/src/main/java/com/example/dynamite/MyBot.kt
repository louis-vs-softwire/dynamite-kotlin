package com.example.dynamite

import com.example.dynamite.helpers.getCounter
import com.example.dynamite.strategies.*
import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move
import kotlin.math.floor

class MyBot : Bot {
    private var currentStrategy: Strategy = NoStrategy()

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
            is NoStrategy -> currentStrategy = RandomStrategy()
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

                val pattern = detectPattern(analysis)
                if (pattern !is NoStrategy) {
                    currentStrategy = pattern
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
                if (analysis.myDynamiteCount() >= 99) {
                    currentStrategy = RandomStrategy()
                }

            }
            is CounterStrategy -> {
                // use mono strategy to counter mono bots
                if (theirRecentMostFrequentMoveFrequency > 5) {
                    currentStrategy = MonoStrategy(getCounter(theirRecentMostFrequentMove))
                }
            }
            is AntiPatternStrategy -> {
                // if this isn't working, revert to random
                if (analysis.totalWins(analysis.getLastXRounds(5)) < 3) {
                    currentStrategy = RandomStrategy()
                }
            }
        }
    }

    /**
     * Detect if a bot is using a linear(ish) regular pattern of dynamite (every X rounds, x < 20)
     */
    private fun detectPattern(analysis: GameAnalysis) : Strategy {
        for (i in 1..20) {
            var j = 0
            var consistency = 0
            val maxConsistency = floor(analysis.rounds.count().toDouble() / i.toDouble())
            while (j < analysis.rounds.count()) {
                if (analysis.rounds[j].p2 == Move.D) consistency++
                j += i
            }
            if (consistency.toDouble() / maxConsistency > 0.7) AntiPatternStrategy(i)
        }
        return NoStrategy()
    }

}