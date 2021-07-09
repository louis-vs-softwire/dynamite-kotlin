//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.softwire.dynamite.runner
import com.example.dynamite.Factory
import com.example.dynamite.MyBot
import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move
import com.softwire.dynamite.game.Round
import com.softwire.dynamite.opponents.*
import java.util.*
object DynamiteRunner {
    fun playGames(playerBotFactory: Factory<MyBot>): Results {
        val opponentBots = arrayOf(
            RockBot(),
            PaperBot(),
            ScissorsBot(),
            RandomRPSBot(),
            DynamiteFirstBot(),
            DynamiteOnDrawBot(),
            BeatTheirPreviousMoveBot()
        )
        val results = Results()
        val var4 = opponentBots.size
        for (var5 in 0 until var4) {
            val opponentBot = opponentBots[var5]
            val result =
                runGame(playerBotFactory.create(), opponentBot)
            results.addResults(result)
        }
        println(results.toString())
        return results
    }
    private fun runGame(playerBot: Bot, opponentBot: Bot): Result {
        val result = Result()
        result.opponentName = opponentBot.javaClass.simpleName
        val playerGameState =
            setupGameState()
        val opponentGameState =
            setupGameState()
        var playerScore = 0
        var opponentScore = 0
        var currentRoundWorth = 1
        while (true) {
            var playerMove: Move
            try {
                playerMove = playerBot.makeMove(playerGameState)
            } catch (var12: Exception) {
                result.result = "LOSE"
                result.message = "Your bot threw an exception: " + var12.message
                break
            }
            var opponentMove: Move
            try {
                opponentMove = opponentBot.makeMove(opponentGameState)
            } catch (var11: Exception) {
                result.result = "WIN"
                result.message = "Opponent bot threw an exception: " + var11.message
                break
            }
            addRound(
                playerGameState,
                opponentGameState,
                playerMove,
                opponentMove
            )
            if (xWinsAgainstY(
                    playerMove,
                    opponentMove
                )
            ) {
                playerScore += currentRoundWorth
                currentRoundWorth = 1
            } else if (xWinsAgainstY(
                    opponentMove,
                    playerMove
                )
            ) {
                opponentScore += currentRoundWorth
                currentRoundWorth = 1
            } else {
                ++currentRoundWorth
            }
            if (playerScore >= 1000) {
                result.result = "WIN"
                break
            }
            if (opponentScore >= 1000) {
                result.result = "LOSE"
                break
            }
            if (playerGameState.rounds.size >= 2500) {
                result.result = "DRAW"
                break
            }
        }
        result.yourScore = playerScore
        result.opponentScore = opponentScore
        result.rounds = playerGameState.rounds
        println(result.toString())
        return result
    }
    private fun xWinsAgainstY(x: Move, y: Move): Boolean {
        return if (x != Move.R || y != Move.S && y != Move.W) {
            if (x != Move.P || y != Move.R && y != Move.W) {
                if (x == Move.S && (y == Move.P || y == Move.W)) {
                    true
                } else if (x != Move.D || y != Move.R && y != Move.P && y != Move.S) {
                    x == Move.W && y == Move.D
                } else {
                    true
                }
            } else {
                true
            }
        } else {
            true
        }
    }
    private fun setupGameState(): Gamestate {
        val playerGamestate = Gamestate()
        playerGamestate.rounds = ArrayList<Round>()
        return playerGamestate
    }
    private fun addRound(
        playerGamestate: Gamestate,
        opponentGamestate: Gamestate,
        playerMove: Move,
        opponentMove: Move
    ) {
        val playerRound = Round()
        playerRound.p1 = playerMove
        playerRound.p2 = opponentMove
        playerGamestate.rounds.add(playerRound)
        val opponentRound = Round()
        opponentRound.p1 = opponentMove
        opponentRound.p2 = playerMove
        opponentGamestate.rounds.add(opponentRound)
    }
}