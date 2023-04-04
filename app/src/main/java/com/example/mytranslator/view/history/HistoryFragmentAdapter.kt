package com.example.mytranslator.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.messages.WordData
import com.example.mytranslator.R

class HistoryFragmentAdapter :
    RecyclerView.Adapter<HistoryFragmentAdapter.RecyclerItemViewHolder>() {

    private var data: List<WordData> = mutableListOf()

    fun setData(data: List<WordData>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.history_item, parent, false)
                    as View
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val word: AppCompatTextView = itemView.findViewById(R.id.history_item_word)

        fun bind(data: WordData) {
            word.text = data.word
        }
    }
}