package com.example.newsapp.presentation

import kotlin.random.Random

class Utils {
    companion object {
        fun generateRandomWords(wordCount: Int): String {
            val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            val stringBuilder = StringBuilder()

            repeat(wordCount) {
                val wordLength = Random.nextInt(1, 8)

                repeat(wordLength) {
                    val randomIndex = Random.nextInt(characters.length)
                    stringBuilder.append(characters[randomIndex])
                }

                if (it < wordCount - 1) {
                    stringBuilder.append(" ")
                }
            }

            return stringBuilder.toString()
        }
    }
}