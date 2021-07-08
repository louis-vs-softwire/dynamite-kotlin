package com.example.dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move

/**
 * A bot which somehow performs worse than the AttritionBot.
 *
 * This bot picks the least frequent move of the game so far, whilst ensuring that turns are not
 * wasted due to lack of dynamite.
 */
class LeastFrequentMoveBot : Bot {
    override fun makeMove(gamestate: Gamestate): Move {
        // Are you debugging?
        // Put a breakpoint in this method to see when we make a move
        val moveFrequencies = hashMapOf(
            Move.P to 0,
            Move.S to 0,
            Move.R to 0,
            Move.D to 0,
            Move.W to 0
        )

        var dynamiteCount = 0
        for (move in gamestate.rounds) {
            moveFrequencies[move.p1] = moveFrequencies[move.p1]?.inc() ?: 1 // elvis operator magic!
            moveFrequencies[move.p2] = moveFrequencies[move.p2]?.inc() ?: 1

            // magic! converts bools to ints then adds
            dynamiteCount += (move.p1 == Move.D).compareTo(false) + (move.p2 == Move.D).compareTo(false)
        }

        var chosenMove = Move.P
        var nextBest = Move.P
        var min = Int.MAX_VALUE
        for (pair in moveFrequencies) {
            if (pair.value < min) {
                min = pair.value
                nextBest = chosenMove
                chosenMove = pair.key
            }
        }

        // don't use too many dynamite!
        if (chosenMove == Move.D) {
            dynamiteCount++
            if (dynamiteCount >= 100) {
                chosenMove = nextBest
            }
        }

        return chosenMove
    }

    init {
        // Are you debugging?
        // Put a breakpoint on the line below to see when we start a new match
        println("Started new match")
    }
}