package com.example.dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move
import kotlin.random.Random

/**
 * This fairly dumb bot counters the dynamite first bot by using water bombs for the first 100
 * moves, and dynamite for the next 100. For the rest of the rounds, it simply returns a random
 * move.
 */
class AttritionBot : Bot {
    override fun makeMove(gamestate: Gamestate): Move {
        // Are you debugging?
        // Put a breakpoint in this method to see when we make a move
        return when {
            gamestate.rounds.size <= 100 -> Move.W
            gamestate.rounds.size <= 200 -> Move.D
            else -> randomMove()
        }
    }

    private fun randomMove(): Move {
        return when (Random.Default.nextInt(1, 4)) {
            1 -> Move.R
            2 -> Move.P
            3 -> Move.S
            else -> Move.S
        }
    }

    init {
        // Are you debugging?
        // Put a breakpoint on the line below to see when we start a new match
        println("Started new match")
    }
}