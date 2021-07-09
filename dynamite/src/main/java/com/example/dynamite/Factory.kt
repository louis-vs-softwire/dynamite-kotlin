package com.example.dynamite
import com.softwire.dynamite.bot.Bot
interface Factory<T : Bot> {
    fun create(): T
}