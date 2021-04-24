package com.android.opus.ui.screen.skillscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.android.opus.R
import com.android.opus.domain.SkillsScreenInteractor
import com.android.opus.domain.SkillsScreenMockData
import com.android.opus.model.SkillsScreenField
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import kotlinx.android.synthetic.main.activity_skills_screen.*
import kotlinx.coroutines.Dispatchers

class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen),
    android.widget.SearchView.OnQueryTextListener{

    private var viewModel = SkillsScreenViewModel(
        SkillsScreenInteractor(dispatcher = Dispatchers.Default)
    )

    private val chosenSkillAdapter = ChosenSkillAdapter{ Id -> SkillsScreenMockData.removeData(Id);
        updateAdapter(SkillsScreenMockData.getResult());
        updateAdapterOfCS((SkillsScreenMockData.getNewData()))}

    private val SCAdapter = SkillsScreenAdapter { skillId -> SkillsScreenMockData.addData(skillId);
        updateAdapter(SkillsScreenMockData.getResult());
        updateAdapterOfCS(SkillsScreenMockData.getNewData()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSCAdapter()
        SkillsScreenMockData.refreshDisplayableList()
        viewModel.SCFields.observe(this.viewLifecycleOwner, this::updateAdapter)
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    private fun setUpSCAdapter() {
         offeredSkills.layoutManager = ChipsLayoutManager.newBuilder(context)
            .setScrollingEnabled(false)
            .setOrientation(ChipsLayoutManager.HORIZONTAL)
            .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
            .build()
        offeredSkills.addItemDecoration(SpacingItemDecoration(25, 15))
        offeredSkills?.adapter = SCAdapter

        chosenSkills?.layoutManager =ChipsLayoutManager.newBuilder(context)
            .setScrollingEnabled(true)
            .setOrientation(ChipsLayoutManager.HORIZONTAL)
            .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
            .build()
        chosenSkills?.adapter = chosenSkillAdapter
        chosenSkills.addItemDecoration(SpacingItemDecoration(25, 10))

    }

    private fun updateAdapter(list: List<SkillsScreenField>?) {
        SCAdapter.submitList(list?.let { ArrayList(it) })
    }

    private fun updateAdapterOfCS(list: List<SkillsScreenField>?){
        chosenSkillAdapter.submitList(list?.let { ArrayList(it) })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) searchSkill(query)
        if (query == null) {
            SkillsScreenMockData.refreshDisplayableList()
            updateAdapter(SkillsScreenMockData.getResult())
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) searchSkill(query)
        return true
    }

    private fun searchSkill(query: String){
        updateAdapter(SkillsScreenMockData.searchItem(query))
    }

    companion object {
        fun newInstance(): SkillsScreenFragment = SkillsScreenFragment()
    }
}
