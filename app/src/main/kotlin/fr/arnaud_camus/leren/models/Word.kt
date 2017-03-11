package fr.arnaud_camus.leren.models

import android.content.Context
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*
import android.telecom.Conference
import com.opencsv.CSVReader
import io.realm.annotations.PrimaryKey
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader


/**
 * Created by arnaud on 3/5/16.
 */

open class Word: RealmObject() {
    open var dutchFirst: Boolean =  false
    @PrimaryKey open var original: String = ""
    open var translation: String = ""
    open var categoryName: String? = null

    fun initWith(english: String, dutch: String, categoryName: String? = null): Word {
        this.categoryName = categoryName
        original = if (dutchFirst) dutch else english
        translation = if (!dutchFirst) dutch else english
        return this
    }

    fun forceDutchFirst(dutchFirst: Boolean) {
        if (this.dutchFirst == dutchFirst) return
        Realm.getDefaultInstance().beginTransaction()
        this.dutchFirst = dutchFirst

        val temp = original
        original = translation
        translation = temp
        Realm.getDefaultInstance().commitTransaction()
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

    companion object {
        fun parseInputStream(stream: InputStreamReader): List<Word> {
            val dictionary = mutableListOf<Word>()
            try {
                val reader = CSVReader(stream, '\t')
                reader.readNext() // file headline
                var nextLine: Array<String>? = reader.readNext()
                while (nextLine != null) {
                    if (nextLine.size > 1) {
                        dictionary.add(Word().initWith(nextLine.first(), nextLine.get(1)))
                    }
                    nextLine = reader.readNext()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return dictionary
        }
    }
}
