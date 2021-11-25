package com.android.opus.ui.screen.activityfield

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.opus.R
import com.android.opus.domain.ActivityFieldInteractor
import com.android.opus.model.FieldOfActivity
import kotlinx.android.synthetic.main.fragment_activity_field_screen.*
import kotlinx.coroutines.Dispatchers
import kotlin.math.roundToInt

class ActivityFieldFragment : Fragment(R.layout.fragment_activity_field_screen) {

    private lateinit var viewModel: ActivityFieldViewModel
    private val activityFieldAdapter = ActivityFieldAdapter()

    private var listener: BtnNextClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BtnNextClickListener) {
            listener = context
        }
        viewModel = ActivityFieldViewModel(
            ActivityFieldInteractor(dispatcher = Dispatchers.Default)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBase?.setOnClickListener {
            listener?.onClick()
        }
        setUpActivityFieldAdapter()
        viewModel.activityFields.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    private fun setUpActivityFieldAdapter() {
        itemsFieldOfActivity?.layoutManager = GridLayoutManager(requireContext(), 2)
        itemsFieldOfActivity?.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                if (parent.getChildLayoutPosition(view) % 2 == 1) {
                    outRect.left = resources.getDimension(R.dimen.spacing_decor).toInt()
                }
            }

        })
        itemsFieldOfActivity?.adapter = activityFieldAdapter
    }

    private fun updateAdapter(activityFields: List<FieldOfActivity>?) {
        activityFieldAdapter.submitList(activityFields)
    }

    interface BtnNextClickListener {
        fun onClick()
    }

    companion object {
        fun newInstance(): ActivityFieldFragment = ActivityFieldFragment()
    }
}
