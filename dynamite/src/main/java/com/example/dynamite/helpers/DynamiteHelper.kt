package com.example.dynamite.helpers

import com.softwire.dynamite.game.Move
import com.softwire.dynamite.game.Round
import kotlin.math.floor
import kotlin.random.Random

/**
 * Finds out who won the round
 */
fun roundOutcome(round: Round) : RoundOutcome {
    if (round.p1 == round.p2) return RoundOutcome.DRAW // draw
    if (round.p1 != Move.W && round.p2 == Move.D) return RoundOutcome.LOSS // p2 uses dynamite
    return when (round.p1) {
        Move.R -> if (round.p2 == Move.P) RoundOutcome.LOSS else RoundOutcome.WIN
        Move.P -> if (round.p2 == Move.S) RoundOutcome.LOSS else RoundOutcome.WIN
        Move.S -> if (round.p2 == Move.R) RoundOutcome.LOSS else RoundOutcome.WIN
        Move.W -> if (round.p2 == Move.D) RoundOutcome.WIN else RoundOutcome.LOSS
        Move.D -> if (round.p2 == Move.W) RoundOutcome.LOSS else RoundOutcome.WIN
        else -> RoundOutcome.DRAW // should never happen
    }
}

fun getCounter(move: Move) : Move {
    return when (move) {
        Move.R -> Move.P
        Move.P -> Move.S
        Move.S -> Move.R
        Move.D -> Move.W
        Move.W -> Move.P // this one could be anything
        else -> Move.P // should never happen
    }
}

fun randomMove(canUseDynamite: Boolean, theyCanUseDynamite: Boolean): Move {
    return when (floor(Math.random() * 6.0).toInt() + 1) {
        1 -> Move.R
        2 -> Move.P
        3 -> Move.S
        4 -> if (theyCanUseDynamite) Move.W else Move.R
        5 -> if (canUseDynamite) Move.D else Move.P
        else -> Move.S
    }
}
