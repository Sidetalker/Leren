package fr.arnaud_camus.leren.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.include_configuration.view.*

/**
 * Created by arnaud on 5/29/16.
 */
class LanguageConfiguration: LinearLayout {

    enum class PRIMARY_LANGUAGE {
        ENGLISH,
        DUTCH
    }

    interface LanguageConfigurationChange {
        fun onLanguageConfigurationChange(newPrimaryLanguage: PRIMARY_LANGUAGE)
    }

    var primaryLanguage: PRIMARY_LANGUAGE = PRIMARY_LANGUAGE.ENGLISH
    var callBack: LanguageConfigurationChange? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        switchLanguage.setOnClickListener({
            val temp = fromLanguage.text
            fromLanguage.setText(toLanguage.text)
            toLanguage.setText(temp)

            primaryLanguage = if (primaryLanguage == PRIMARY_LANGUAGE.ENGLISH) {
                PRIMARY_LANGUAGE.DUTCH
            } else {
                PRIMARY_LANGUAGE.ENGLISH
            }
            callBack?.onLanguageConfigurationChange(primaryLanguage)
        })
    }

    fun setOnLanguageConfigurationChange(languageConfiguration: LanguageConfigurationChange) {
        callBack = languageConfiguration
    }
}