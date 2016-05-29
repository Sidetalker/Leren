package fr.arnaud_camus.leren.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.arnaud_camus.leren.LerenApplication
import fr.arnaud_camus.leren.R
import fr.arnaud_camus.leren.models.Word
import java.util.*

/**
 * Created by arnaud on 3/5/16.
 */
class LearnFragment: Fragment() {
    private var contentTextView: TextView? = null
    private var categoryName: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_learn, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentTextView = view?.findViewById(R.id.textView) as TextView
        categoryName = view?.findViewById(R.id.categoryName) as TextView

        contentTextView?.setOnClickListener { displayNextWord() }
        displayNextWord()
    }

    fun displayNextWord() {
        val index: Int = Random().nextInt((activity.application as LerenApplication).mockData.size - 1)
        val word = (activity.application as LerenApplication).mockData[index]

        var string = SpannableString(getString(if (word.dutchFirst) R.string.x_means_y else R.string.x_is_translated_y,
                word.original,
                word.translation))
        val orangeSpan = ForegroundColorSpan(ResourcesCompat.getColor(resources, R.color.colorPrimary, activity.theme))
        val blueSpan = ForegroundColorSpan(ResourcesCompat.getColor(resources, R.color.colorAccent, activity.theme))

        string.setSpan(if (word.dutchFirst) orangeSpan else blueSpan,
                0, word.original.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        string.setSpan(if (word.dutchFirst) blueSpan else orangeSpan,
                string.indexOf(word.translation!!), string.indexOf(word.translation) + word.translation.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        contentTextView?.setText(string)

        categoryName?.setText(if (word.categoryName != null) word.categoryName else getString(R.string.uncategorized))
    }

}
