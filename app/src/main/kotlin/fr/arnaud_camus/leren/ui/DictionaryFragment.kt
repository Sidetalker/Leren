package fr.arnaud_camus.leren.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.arnaud_camus.leren.LerenApplication
import fr.arnaud_camus.leren.R
import fr.arnaud_camus.leren.models.Word
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by arnaud on 4/17/16.
 */

class DictionaryFragment: Fragment() {
    private var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val realmConfig = RealmConfiguration.Builder(context).build()

        realm = Realm.getInstance(realmConfig)

        realm.beginTransaction()
        var word = realm.createObject(Word::class.java)
        word.initWith("learn", "leren", "verb")
        realm.commitTransaction()

        realm.beginTransaction()
        var word1 = realm.createObject(Word::class.java)
        word1.initWith("walk", "lopen", "verb")
        realm.commitTransaction()

        realm.beginTransaction()
        var word2 = realm.createObject(Word::class.java)
        word2.initWith("speak", "spreken", "verb")
        realm.commitTransaction();

        realm.beginTransaction()
        val res = realm.where(Word::class.java).findAll()
        Log.i("realm", res.count().toString())
        realm.commitTransaction()

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
