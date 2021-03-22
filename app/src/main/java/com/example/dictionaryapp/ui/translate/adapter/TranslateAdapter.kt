package com.example.dictionaryapp.ui.translate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Translate

class TranslateAdapter : RecyclerView.Adapter<TranslateAdapter.TranslatesViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(translates: List<Translate>) {
        differ.submitList(translates)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslatesViewHolder {
        return TranslatesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_translate, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TranslatesViewHolder, position: Int) {
        val translate = differ.currentList[position]
        holder.bind(translate)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Translate>() {
            override fun areItemsTheSame(oldItem: Translate, newItem: Translate): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Translate, newItem: Translate): Boolean {
                return oldItem == newItem
            }

        }
    }

    class TranslatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(translate: Translate) {
            itemView.findViewById<TextView>(R.id.translateText).text = translate.phrase
        }
    }
}