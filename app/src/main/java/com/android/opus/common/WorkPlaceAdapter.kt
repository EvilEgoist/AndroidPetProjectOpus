package com.android.opus.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
                    .into(photo_work_place)
            } else {
                val params =
                    job_title.layoutParams as LinearLayout.LayoutParams
                params.setMargins(
                    MARGIN_CHANGE_DP,
                    ZERO_MARGIN_DP,
                    ZERO_MARGIN_DP,
                    ZERO_MARGIN_DP
                )
                job_title.layoutParams = params
                place_description.layoutParams = params
                photo_work_place.visibility = View.GONE
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

    companion object {
        const val MARGIN_CHANGE_DP = 24
        const val ZERO_MARGIN_DP = 0
    }
}
