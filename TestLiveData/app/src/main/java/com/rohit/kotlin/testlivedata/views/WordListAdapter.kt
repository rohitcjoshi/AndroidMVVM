package com.rohit.kotlin.testlivedata.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.testlivedata.R
import com.rohit.kotlin.testlivedata.data.Word

class WordListAdapter(val context: Context): RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {
    private var wordsList = emptyList<Word>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.wordItemView.text = wordsList[position].word
    }

    fun setWordsList(words: List<Word>) {
        this.wordsList = words
        notifyDataSetChanged()
    }

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }
}