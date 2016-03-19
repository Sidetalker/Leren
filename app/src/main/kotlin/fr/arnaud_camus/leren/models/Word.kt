package fr.arnaud_camus.leren.models

import android.util.Log
import java.util.*

/**
 * Created by arnaud on 3/5/16.
 */
class Word {
    var dutchFirst: Boolean
    var original: String
    var translation: String
    var categoryName: String? = null

    constructor(english: String, dutch: String, categoryName: String? = null) {
        dutchFirst = Random().nextBoolean()

        this.categoryName = categoryName
        original = if (dutchFirst) dutch else english
        translation = if (!dutchFirst) dutch else english
    }

    fun forceDutchFirst(dutchFirst: Boolean) {
        if (this.dutchFirst == dutchFirst) return
        this.dutchFirst = dutchFirst

        val temp = original
        original = translation
        translation = temp
    }

    fun checkTranslation(text: String): Boolean {
        val normalizedTranslation = normalizeText(translation)
        val normalizedText = normalizeText(text)
        Log.d("debug", normalizedText + " ---- " + normalizedTranslation)
        return normalizedText.compareTo(normalizedTranslation) == 0
    }

    fun resultByWords(text: String): ArrayList<Result> {
        val words = translation.split(" ")
        val inputWords = text.split(" ")
        val results: ArrayList<Result> = ArrayList()

        var i = 0
        for (w in inputWords) {
            val correct = words.count() > i
                    && normalizeText(w).compareTo(normalizeText(words[i])) == 0
            results.add(Result(word = w, correct = correct))
            Log.d("debug", results.last().toString())
            i++
        }

        return results
    }

    private fun normalizeText(text: String): String {
        return text.toLowerCase().replace(Regex("[^a-zA-Z0-9]"), "")
    }
}
