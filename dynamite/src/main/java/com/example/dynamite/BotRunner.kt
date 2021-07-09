package com.example.dynamite

import com.softwire.dynamite.runner.*

object BotRunner {
    @JvmStatic
    fun main(args: Array<String>) {
        val f: Factory<StrategyBot> = StrategyBot
        val results = (1..10).map { DynamiteRunner.playGames(f) }
        val avgWins = results.map { x -> x.results.sumBy { (it.result == "WIN").compareTo(false) } }.average()
        val avgLosses = results.map { x -> x.results.sumBy { (it.result == "LOSE").compareTo(false) } }.average()
        print("\nAverage Wins: $avgWins\nAverage Losses: $avgLosses\n")
    }

    // for uploading to website
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val f: Factory<MyBot> = MyBot
//        val results: Results = DynamiteRunner.playGames(f)
//    }
}