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
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlin.properties.Delegates

/**
 * Created by arnaud on 4/17/16.
 */

class DictionaryFragment: Fragment() {
    private var realm: Realm by Delegates.notNull()
    lateinit var listAdapter: DictionaryAdapter

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
                var word = realm.createObject(Word::class.java)
                word.initWith(englishInput.text.toString(), dutchInput.text.toString())
                realm.commitTransaction()
                englishInput.text.clear()
                dutchInput.text.clear()
//                reloadData()
            }
        }

        realm.beginTransaction()
        val res = realm.where(Word::class.java).findAll()
        res.sort("original")
        Log.i("realm", res.count().toString())
        realm.commitTransaction()

        with (list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            listAdapter = DictionaryAdapter(res)
            adapter = listAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
