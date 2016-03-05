package fr.arnaud_camus.leren.models

import java.util.*

/**
 * Created by arnaud on 3/5/16.
 */
class Word {
    val dutchFirst: Boolean
    var original: String
    var translation: String
    var categoryName: String? = null

    constructor(english: String, dutch: String, categoryName: String? = null) {
        dutchFirst = Random().nextBoolean()

        if (dutchFirst) {
            original = dutch
            translation = english
        } else {
            original = english
            translation = dutch
        }
    }

}
