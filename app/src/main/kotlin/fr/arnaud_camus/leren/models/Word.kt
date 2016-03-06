package fr.arnaud_camus.leren.models

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
        return translation == text || original == text
    }
}
