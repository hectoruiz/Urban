package com.urban.androidhomework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_character_list.view.*

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null
    var charactersList: List<String> = ArrayList()

    fun setCharacters(charactersList: List<String>) {
        this.charactersList = charactersList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val characterName: TextView = itemView.character_item_name

        fun bind(name: String, position: Int) {
            itemView.setOnClickListener {
                onItemClick?.invoke(charactersList[position])
            }
            characterName.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_character_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(charactersList[position], position)
    }

    override fun getItemCount(): Int = charactersList.size

}