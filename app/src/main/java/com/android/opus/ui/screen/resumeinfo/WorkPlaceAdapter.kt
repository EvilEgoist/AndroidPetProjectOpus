package com.android.opus.ui.screen.resumeinfo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.model.WorkPlace
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_work_place.view.*

class WorkPlaceAdapter :
    ListAdapter<WorkPlace, WorkPlaceAdapter.WorkPlaceViewHolder>(WorkPlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkPlaceViewHolder =
        WorkPlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_work_place, parent, false)
        )

    override fun onBindViewHolder(holder: WorkPlaceViewHolder, position: Int) {
        val workPlace = getItem(position)
        Glide.with(holder.itemView.context)
            .load(workPlace.imageUrl)
            .into(holder.itemView.photo)
        holder.itemView.job_title.text = workPlace.title
        holder.itemView.place_description.text = workPlace.placeDescription
        holder.itemView.job_position.text = workPlace.jobPosition
        holder.itemView.work_term.text = workPlace.workPeriod
        if (workPlace.responsibilities.isNullOrEmpty()) {
            holder.itemView.responsibilities_title.visibility = View.GONE
            holder.itemView.responsibilities_description.visibility = View.GONE
        } else {
            holder.itemView.responsibilities_description.text = workPlace.responsibilities
        }
    }

    class WorkPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

private class WorkPlaceDiffCallback : DiffUtil.ItemCallback<WorkPlace>() {
    override fun areItemsTheSame(oldItem: WorkPlace, newItem: WorkPlace): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WorkPlace, newItem: WorkPlace): Boolean =
        oldItem == newItem
}
