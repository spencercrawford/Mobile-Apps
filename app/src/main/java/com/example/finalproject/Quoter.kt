package com.example.finalproject

import java.io.InputStream
import java.util.ArrayList
import java.util.Random
import java.util.Scanner

class Quoter(var quotesFile: InputStream) {

    var quotes: ArrayList<Quote> = ArrayList()
    var randIndex: Random
    var currRandIndex: Int = 0
    val randomQuote: Quote

        get() {

            var index = randIndex.nextInt(quotes.size)
            while (index == currRandIndex) {
                index = randIndex.nextInt(quotes.size)
            }
            val newQuote = quotes[index]
            currRandIndex = index
            return newQuote
        }

    inner class Quote(var text: String?, var author: String?)

    init {
        readFile()
        randIndex = Random()
        currRandIndex = -1
    }

    fun readFile() {
        val scanner: Scanner
        try {
            scanner = Scanner(quotesFile)
            while (scanner.hasNextLine()) {
                quotes.add(Quote(scanner.nextLine(), scanner.nextLine()))
            }
        } catch (e: Exception) {
            println(e)
        }
        println(quotes)
    }
}