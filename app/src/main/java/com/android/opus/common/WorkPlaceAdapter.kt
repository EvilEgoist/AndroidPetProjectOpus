package com.android.opus.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.diff.WorkPlaceDiffCallback
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
        with(holder.itemView) {
            if (workPlace.imageUrl != null) {
                Glide.with(context)
                    .load(workPlace.imageUrl)
                    .into(photo)
            } else {
                val params =
                    job_title.layoutParams as LinearLayout.LayoutParams
                params.setMargins(24, 0, 0, 0)
                job_title.layoutParams = params
                place_description.layoutParams = params
                photo.visibility = View.GONE
            }
            job_title.text = workPlace.title
            place_description.text = workPlace.placeDescription
            job_position.text = workPlace.jobPosition
            work_term.text = workPlace.workPeriod
            if (workPlace.responsibilities.isNullOrEmpty()) {
                responsibilities_title.visibility = View.GONE
                responsibilities_description.visibility = View.GONE
            } else {
                responsibilities_description.text = workPlace.responsibilities
            }
        }
    }

    class WorkPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
