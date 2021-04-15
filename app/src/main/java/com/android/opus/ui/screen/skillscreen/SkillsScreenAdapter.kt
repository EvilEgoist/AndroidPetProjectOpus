package com.android.opus.ui.screen.skillscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import kotlinx.android.synthetic.main.item_skills_screen.view.*

class SkillsScreenAdapter(private val items: ArrayList<String>): RecyclerView.Adapter<SkillsScreenViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsScreenViewHolder =
        SkillsScreenViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_skills_screen, parent, false)
        )

    override fun onBindViewHolder(holder: SkillsScreenViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.item_skills.text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

private class SkillsScreenDiffCallback:DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        areContentsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        (oldItem == newItem)
}

class SkillsScreenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(title: String) {
        itemView.item_skills.text = title
    }
}

