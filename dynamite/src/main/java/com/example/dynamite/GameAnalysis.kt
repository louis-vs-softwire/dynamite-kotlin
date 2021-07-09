package com.example.dynamite

import com.example.dynamite.helpers.RoundOutcome
import com.example.dynamite.helpers.roundOutcome
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move
import com.softwire.dynamite.game.Round

/**
 * This class produces an analysis of the current gamestate, and provides a useful API to retrieve
 * information about the current game.
 */
class GameAnalysis(gamestate: Gamestate) {
    var rounds: List<Round> = gamestate.rounds
    var moveFrequencies: HashMap<Move, Int>

    init {
        moveFrequencies = calculateMoveFrequencies(getMovesFromRounds(gamestate.rounds))
    }

    /**
     * The number of dynamites used by your bot
     */
    fun myDynamiteCount(selectedRounds: List<Round> = rounds) : Int {
        return selectedRounds.fold(0) { acc: Int, round: Round -> if (round.p1 == Move.D) acc + 1 else acc }
    }

    /**
     * The number of dynamites used by the opponent
     */
    fun theirDynamiteCount(selectedRounds: List<Round> = rounds) : Int {
        return selectedRounds.fold(0) { acc: Int, round: Round -> if (round.p2 == Move.D) acc + 1 else acc }
    }

    /**
     * The total number of rounds played so far
     */
    fun totalRounds() : Int {
        return rounds.count()
    }

    /**
     * The total number of wins for p1
     */
    fun totalWins(selectedRounds: List<Round> = rounds) : Int {
        return selectedRounds.fold(0) { acc: Int, round: Round -> if (roundOutcome(round) == RoundOutcome.WIN) acc + 1 else acc }
    }

    /**
     * The total number of losses for p1
     */
    fun totalLosses(selectedRounds: List<Round> = rounds) : Int {
        return selectedRounds.fold(0) { acc: Int, round: Round -> if (roundOutcome(round) == RoundOutcome.LOSS) acc + 1 else acc }
    }

    /**
     * The total number of draws
     */
    fun totalDraws(selectedRounds: List<Round> = rounds) : Int {
        return selectedRounds.fold(0) { acc: Int, round: Round -> if (roundOutcome(round) == RoundOutcome.DRAW) acc + 1 else acc }
    }

    /**
     * Extract a list of player 1 moves
     */
    fun myMoves(selectedRounds: List<Round> = rounds): List<Move> {
        return selectedRounds.map { it.p1 }
    }

    /**
     * Extract a list of player 2 moves
     */
    fun theirMoves(selectedRounds: List<Round> = rounds): List<Move> {
        return selectedRounds.map { it.p2 }
    }

    /**
     * Get the last move of your bot
     */
    fun myLastMove() : Move? {
        return rounds.lastOrNull()?.p1
    }

    /**
     * Get the last move of the opponent
     */
    fun theirLastMove() : Move {
        return rounds.last().p2
    }

    fun lastRound() : Round? {
        return rounds.lastOrNull()
    }

    /**
     * Get the past X rounds
     */
    fun getLastXRounds(x: Int) : List<Round> {
        val adjustedX = if (x > rounds.count() - 1) rounds.count() - 1 else x
        return rounds.slice((rounds.count() - adjustedX) until rounds.count())
    }

    /**
     * Get all moves from the provided round in list format
     */
    fun getMovesFromRounds(selectedRounds: List<Round>) : List<Move> {
        return selectedRounds.flatMap { listOf(it.p1, it.p2) }
    }

    /**
     * The most frequent move played by your bot
     */
    fun myMostFrequentMove(historyLength: Int = rounds.count() - 1) : Move {
        val moveList = getLastXRounds(historyLength).map {
            it.p1
        }
        return mostFrequentMove(moveList)
    }

    /**
     * The most frequent move in a list of moves
     */
    fun mostFrequentMove(moveList: List<Move>) : Move {
        var max = 0
        var selectedMove = Move.P // default move is P, this ought be a parameter
        for (moveFrequency in calculateMoveFrequencies(moveList)) {
            if (moveFrequency.value > max) {
                selectedMove = moveFrequency.key
                max = moveFrequency.value
            }
        }

        return selectedMove
    }

    /**
     * The most frequent move in the rounds provided
     */
    fun mostFrequentMoveFromRounds(selectedRounds: List<Round>) : Move {
        return mostFrequentMove(getMovesFromRounds(selectedRounds))
    }

    /**
     * Calculate the frequencies of all moves in a list
     */
    fun calculateMoveFrequencies(moves: List<Move>) : HashMap<Move, Int> {
        val moveFrequencies = hashMapOf(
            Move.P to 0,
            Move.S to 0,
            Move.R to 0,
            Move.D to 0,
            Move.W to 0
        )

        for (move in moves) {
            moveFrequencies[move] = moveFrequencies[move]?.inc() ?: 1 // elvis operator magic!
        }

        return moveFrequencies
    }

}