package com.android.opus.ui.screen.activityfield

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.android.opus.R
import com.android.opus.domain.ActivityFieldInteractor
import kotlinx.android.synthetic.main.fragment_activity_field_screen.*
import kotlinx.coroutines.Dispatchers

class ActivityFieldFragment : Fragment(R.layout.fragment_activity_field_screen) {

    private lateinit var viewModel : ActivityFieldViewModel
    private val activityFieldAdapter = ActivityFieldAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadActivityFieldList()
        setUpActivityFieldAdapter()
        viewModel.activityFields.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ActivityFieldViewModel(
            ActivityFieldInteractor(
                dispatcher = Dispatchers.Default,
                context = context
            )
        )
    }
    private fun setUpActivityFieldAdapter() {
        items_field_of_activity?.layoutManager = GridLayoutManager(requireContext(), 2)
        items_field_of_activity?.adapter = activityFieldAdapter
    }

    private fun updateAdapter(activityFields: List<String>?) {
        activityFieldAdapter.submitList(activityFields)
    }

    companion object {
        fun newInstance(): ActivityFieldFragment = ActivityFieldFragment()
    }
}
