package com.android.opus.ui.screen.skillscreen

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.android.opus.R
import com.android.opus.domain.ActivityFieldInteractor
import com.android.opus.domain.SkillsScreenInteractor
import com.android.opus.ui.screen.activityfield.ActivityFieldFragment
import com.android.opus.ui.screen.activityfield.ActivityFieldViewModel
import kotlinx.android.synthetic.main.activity_skills_screen.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.collections.ArrayList

class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen){

    private lateinit var viewModel: SkillsScreenViewModel
    val res: Resources = resources
    val list = ArrayList<String>()
    private lateinit var skillsScreenAdapter: SkillsScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSkillsScreenList()
        setUpSkillsScreenAdapter()
        viewModel.skillsScreen.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = SkillsScreenViewModel(
                SkillsScreenInteractor(
                        dispatcher = Dispatchers.Default,
                        context = context
                )
        )
    }

    fun setUpSkillsScreenAdapter(){
        val data = res.getStringArray(R.array.skills_arr)
        list.addAll(data)
        skillsScreenAdapter = SkillsScreenAdapter(list)
        offeredSkills?.layoutManager = GridLayoutManager(requireContext(), 4)
        offeredSkills?.adapter = skillsScreenAdapter
    }

    private fun updateAdapter(activityFields: List<String>?) {
        skillsScreenAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): SkillsScreenFragment = SkillsScreenFragment()
    }
}