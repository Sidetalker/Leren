package fr.arnaud_camus.leren.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.arnaud_camus.leren.R
import fr.arnaud_camus.leren.models.Word
import fr.arnaud_camus.leren.ui.views.LanguageConfiguration
import fr.arnaud_camus.leren.utils.CustomDecorationDivider
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_dictionary.*
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by arnaud on 4/17/16.
 */

class DictionaryFragment: Fragment(), LanguageConfiguration.LanguageConfigurationChange {
    private var realm: Realm by Delegates.notNull()
    lateinit var listAdapter: DictionaryAdapter
    var res: ArrayList<Word>? = null

    var languageConfig: LanguageConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveToDictionary.setOnClickListener {
            if (englishInput.text.isNotEmpty() && dutchInput.text.isNotEmpty()) {
                realm.beginTransaction()
                val word = realm.createObject(Word::class.java)
                word.initWith(englishInput.text.toString(), dutchInput.text.toString())
                realm.commitTransaction()
                englishInput.text.clear()
                dutchInput.text.clear()
                res?.add(word)
                reorderList()
                listAdapter.notifyDataSetChanged()
            }
        }

        languageConfig = view?.findViewById(R.id.configuration) as? LanguageConfiguration
        languageConfig?.setOnLanguageConfigurationChange(this)

        realm.beginTransaction()
        res = ArrayList<Word>(realm.where(Word::class.java).findAll())
        realm.commitTransaction()


        reorderList()
        with (list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            listAdapter = DictionaryAdapter(res!!)
            adapter = listAdapter
            addItemDecoration(CustomDecorationDivider(context))
        }
    }

    override fun onLanguageConfigurationChange(newPrimaryLanguage: LanguageConfiguration.PRIMARY_LANGUAGE) {
        reorderList()
        listAdapter.sortedByEnglishWords = languageConfig?.primaryLanguage == LanguageConfiguration.PRIMARY_LANGUAGE.ENGLISH
        listAdapter.notifyDataSetChanged()
        list.smoothScrollToPosition(0)
    }

    fun reorderList() {
        res?.sortWith( Comparator { w,  w2 ->
            if (languageConfig?.primaryLanguage == LanguageConfiguration.PRIMARY_LANGUAGE.ENGLISH) {
                w.english.toLowerCase().compareTo(w2.english.toLowerCase())
            } else {
                w.dutch.toLowerCase().compareTo(w2.dutch.toLowerCase())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
