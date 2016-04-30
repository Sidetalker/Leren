package fr.arnaud_camus.leren.ui

/**
 * Created by arnaud on 4/30/16.
 */

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.arnaud_camus.leren.R
import fr.arnaud_camus.leren.models.Word
import kotlinx.android.synthetic.main.adapter_dictionary.view.*

class DictionaryAdapter(val mWords: MutableList<Word>) : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.adapter_dictionary, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mWords[position]

        holder.word.text = item.original
        holder.translation.text = item.translation
        holder.firstLetter.text = item.original.first().toString()

        holder.firstLetter.visibility = if (position > 0 && mWords[position-1].original.first() == item.original.first()) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return mWords.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstLetter = view.firstLetter
        val word = view.word
        val translation = view.translation
    }
}
