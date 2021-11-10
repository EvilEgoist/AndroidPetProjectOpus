package com.android.opus.ui.screen.skillscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.android.opus.R
import com.android.opus.domain.SkillsScreenFacade
import com.android.opus.domain.SkillsScreenInteractor
import com.android.opus.model.SkillsScreenField
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import kotlinx.android.synthetic.main.activity_skills_screen.*
import kotlinx.coroutines.Dispatchers


class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen),
    android.widget.SearchView.OnQueryTextListener,
    android.widget.SearchView.OnCloseListener{

    private val viewModel = SkillsScreenViewModel(
        SkillsScreenInteractor(dispatcher = Dispatchers.Default)
    )

    private val chosenSkillAdapter = ChosenSkillAdapter{ id -> viewModel.removeSkill(id) }

    private val SCAdapter = SkillsScreenAdapter { skillId -> viewModel.addSkill(skillId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSCAdapter()
        viewModel.loadedSkills.observe(this.viewLifecycleOwner, this::updateAdapter)
        viewModel.chosenSkills.observe(this.viewLifecycleOwner, this::updateAdapterOfCS)
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener (this)
    }

    private fun setUpSCAdapter() {
         offeredSkills.layoutManager = ChipsLayoutManager.newBuilder(context)
            .setScrollingEnabled(true)
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
        if (query != null) {
            if (query!="") {
                viewModel.searchSkill(query)
            }
        }
        searchView.clearFocus();
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            if (query!="") {
                viewModel.searchSkill(query)
            }
        }
        return true
    }

    override fun onClose(): Boolean {
        searchView.setIconified(false);
        searchView.clearFocus();
        viewModel.refreshFacade()
        return true
    }

    companion object {
        fun newInstance(): SkillsScreenFragment = SkillsScreenFragment()
    }
}
