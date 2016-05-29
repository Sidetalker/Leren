package fr.arnaud_camus.leren.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.arnaud_camus.leren.R
import fr.arnaud_camus.leren.models.Word
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.android.synthetic.main.fragment_practice.*
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by arnaud on 4/17/16.
 */

class DictionaryFragment: Fragment() {
    private var realm: Realm by Delegates.notNull()
    lateinit var listAdapter: DictionaryAdapter
    var res: ArrayList<Word>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val realmConfig = RealmConfiguration.Builder(context).build()
        realm = Realm.getInstance(realmConfig)
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
                res?.sortWith( Comparator { w,  w2 ->
                    w.original.toLowerCase().compareTo(w2.original.toLowerCase())
                })
                listAdapter.notifyDataSetChanged()
            }
        }

        realm.beginTransaction()
        res = ArrayList(realm.where(Word::class.java).findAll())
        realm.commitTransaction()

        res?.sortWith( Comparator { w,  w2 ->
            w.original.toLowerCase().compareTo(w2.original.toLowerCase())
        })
        with (list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            listAdapter = DictionaryAdapter(res!!)
            adapter = listAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
