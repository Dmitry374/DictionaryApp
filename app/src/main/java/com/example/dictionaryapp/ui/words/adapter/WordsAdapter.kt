package com.example.dictionaryapp.ui.words.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Word

class WordsAdapter(private val clickWordListener: (Word) -> Unit) :
    RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(words: List<Word>) {
        differ.submitList(words)
    }

    fun getItems(): MutableList<Word> {
        return differ.currentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_word, parent, false)
        ) { position ->
            clickWordListener(differ.currentList[position])
        }
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        val word = differ.currentList[position]
        holder.bind(word)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem == newItem
            }

        }
    }

    class WordsViewHolder(
        itemView: View,
        private val onWordClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(word: Word) {

            itemView.setOnClickListener { onWordClick(absoluteAdapterPosition) }
            itemView.findViewById<TextView>(R.id.wordText).text = word.phrase
        }
    }
}