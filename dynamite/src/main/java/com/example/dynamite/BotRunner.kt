package com.example.dynamite

import com.softwire.dynamite.runner.*

object BotRunner {
    @JvmStatic
    fun main(args: Array<String>) {
        //run()
        test()
    }

    private fun run() {
        DynamiteRunner.playGames { MyBot() }
    }

    private fun test() {
        val results = (1..10).map { DynamiteRunner.playGames { MyBot() } }
        val avgWins = getAvg(results, "WIN")
        val avgLosses = getAvg(results, "LOSE")
        val avgDraws = getAvg(results, "DRAW")
        print("\nAverage Wins: $avgWins\nAverage Losses: $avgLosses\nAverage Draws: $avgDraws\n")
    }

    private fun getAvg(results: List<Results>, outcome: String) : Double {
        return results.map { x -> x.results.sumBy { (it.result == outcome).compareTo(false) } }.average()
    }
}