package com.example.simbirsoftpracticeapp

import android.util.Log
import java.io.Console
import kotlin.jvm.internal.Intrinsics

interface Publication {

    var price: Double
    var wordCount: Int

    fun getType(): String
}
// task 1
class Book(
    override var price: Double,
    override var wordCount: Int,
) : Publication {

    var name: String? = "Book"

    var author: String? = null

    override fun getType(): String {
        return when (wordCount) {
            in 0..1000 -> "Flash Fiction"
            in 1001..7500 -> "Short Story"
            else -> "Novel"
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (this === other) {
            Log.e("", "References are equals")
            true
        } else {
            Log.e("", "Content are equals")
            this.equals(other)
        }
    }

}

class Magazine(
    override var price: Double,
    override var wordCount: Int
) : Publication {

    override fun getType() = "Magazine"
}

// task 2
class PublicationTest() {

    fun publicationTestMain() {
        var book1 = Book(100.0, 3000)
        var book2 = Book(23.5, 6782)
        var magazine = Magazine(15.99, 2223)

        Log.e(
            "",
            "Book1:\n" +
                    "Type - ${book1.getType()}\n" +
                    "Count of words - ${book1.wordCount}\n" +
                    "Price - ${book1.price}€"
        )

        Log.e(
            "",
            "Book2:\n" +
                    "Type - ${book2.getType()}\n" +
                    "Count of words - ${book2.wordCount}\n" +
                    "Price - ${book2.price}€"
        )
        Log.e(
            "",
            "Magazine:\n" +
                    "Type - ${magazine.getType()}\n" +
                    "Count of words - ${magazine.wordCount}\n" +
                    "Price - ${magazine.price}€"
        )

        fun buy(item: Publication) {
            Log.e("", "The purchase is complete. The purchase amount was ${item.price}")
        }

        // task 3
        book1.name?.let {
            buy(book1)
        }

        book1.author?.let {
            buy(book1)
        }

        // task 4
        val sum: (Int, Int) -> Unit = { x, y ->
            Log.e("", "x + y = ${x + y}")
        }

        sum.invoke(1, 1)
    }
}