package com.android.opus.ui.screen.skillscreen


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.android.opus.R
import com.android.opus.domain.ChosenSkillInteractor
import com.android.opus.domain.SkillsScreenInteractor
import com.android.opus.domain.SkillsScreenMockData
import com.android.opus.model.FieldOfActivity
import com.android.opus.model.SkillsScreenField
import kotlinx.android.synthetic.main.activity_skills_screen.*
import kotlinx.coroutines.Dispatchers

class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen) {

    private lateinit var viewModel : SkillsScreenViewModel
    private lateinit var newViewModel :ChosenSkillViewModel

    private val chosenSkillAdapter = ChosenSkillAdapter{Id -> SkillsScreenMockData.removeData(Id);updateAdapterOfCS((SkillsScreenMockData.getNewData()))}
    private val SCAdapter: SkillsScreenAdapter? = SkillsScreenAdapter { skillId -> SkillsScreenMockData.addData(skillId);updateAdapterOfCS(SkillsScreenMockData.getNewData()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = SkillsScreenViewModel(
            SkillsScreenInteractor(dispatcher = Dispatchers.Default)
        )
        newViewModel = ChosenSkillViewModel(
                ChosenSkillInteractor(dispatcher = Dispatchers.Default)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSCAdapter()
        viewModel.SCFields.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    private fun setUpSCAdapter() {
        offeredSkills?.layoutManager = GridLayoutManager(requireContext(), 4)
        offeredSkills?.adapter = SCAdapter
        chosenSkills?.layoutManager =GridLayoutManager(requireContext(), 4 )
        chosenSkills?.adapter = chosenSkillAdapter
    }

    private fun updateAdapter(list: List<FieldOfActivity>?) {
        SCAdapter?.submitList(list)
    }

    private fun updateAdapterOfCS(list: List<SkillsScreenField>?){
        chosenSkillAdapter.submitList(list?.let { ArrayList(it) })
    }

    companion object {
        fun newInstance(): SkillsScreenFragment = SkillsScreenFragment()
    }
}
