package fr.arnaud_camus.leren.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import fr.arnaud_camus.leren.LerenApplication
import fr.arnaud_camus.leren.R
import fr.arnaud_camus.leren.models.Word
import java.util.*

/**
 * Created by arnaud on 3/6/16.
 */
class PracticeFragment : Fragment() {
    var fromEnglish = true
    var editText: EditText? = null
    var originalWord: TextView? = null
    var word: Word? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_practice, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switchButton = view?.findViewById(R.id.switchLanguage) as ImageButton
        val button = view?.findViewById(R.id.button) as Button
        editText = view?.findViewById(R.id.editText) as EditText
        originalWord = view?.findViewById(R.id.originalWord) as TextView

        switchButton.setOnClickListener({
            val from = view?.findViewById(R.id.fromLanguage) as TextView
            val to = view?.findViewById(R.id.toLanguage) as TextView
            val temp = from.text

            from.setText(to.text)
            to.setText(temp)
            fromEnglish = !fromEnglish
        })

        button.setOnClickListener({
            performCheck()
        })
        editText?.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_GO) {
                performCheck()
            }
            true
        }

        displayNextWord()
    }

    fun displayNextWord() {
        val index: Int = Random().nextInt((activity.application as LerenApplication).mockData.size - 1)
        word = (activity.application as LerenApplication).mockData[index]
        word?.forceDutchFirst(!fromEnglish)

        originalWord?.setText(word?.original)
        editText?.text = null
    }

    private fun performCheck() {
        if (editText?.text!!.length > 0) {
            if (word!!.checkTranslation(editText?.text.toString())) {
                Toast.makeText(context, "Good translation", Toast.LENGTH_SHORT).show()
                displayNextWord()
            } else {
                Toast.makeText(context, "Oops", Toast.LENGTH_SHORT).show()
            }
        }
    }
}