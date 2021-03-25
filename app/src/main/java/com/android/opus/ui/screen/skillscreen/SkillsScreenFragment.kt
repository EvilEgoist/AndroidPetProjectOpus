package com.android.opus.ui.screen.skillscreen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.android.opus.R
import com.android.opus.domain.SkillsScreenInteractor
import kotlinx.android.synthetic.main.activity_skills_screen.*
import kotlinx.coroutines.Dispatchers

class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen){

    private lateinit var viewModel: SkillsScreenViewModel
    private val skillsScreenAdapter = SkillsScreenAdapter()

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
        offeredSkills?.layoutManager = GridLayoutManager(requireContext(), 4)

        offeredSkills?.adapter = skillsScreenAdapter
    }

    private fun updateAdapter(skills: List<String>?){
        skillsScreenAdapter.submitList(skills)
    }

    companion object{
        fun newInstance():SkillsScreenFragment = SkillsScreenFragment()
    }
}