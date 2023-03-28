package com.example.mytranslator.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytranslator.R
import com.example.mytranslator.model.data.WordData

class MainFragmentAdapter(
    val onItemClick: ((wordData: WordData) -> Unit)? = null
) : RecyclerView.Adapter<MainFragmentAdapter.RecyclerItemViewHolder>() {

    private var data: List<WordData> = mutableListOf()

    fun setData(data: List<WordData>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.translation_item, parent, false)
                    as View
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: AppCompatTextView = itemView.findViewById(R.id.translation_item_title)
        private val description: AppCompatTextView =
            itemView.findViewById(R.id.translation_item_description)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])
            }
        }

        fun bind(data: WordData) {
            title.text = data.word
            description.text = data.translation
        }
    }
}