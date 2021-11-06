package com.android.opus.ui.screen.skillscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.opus.R
import com.android.opus.domain.SkillsScreenFacade
import com.android.opus.model.SkillsScreenField
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import kotlinx.android.synthetic.main.activity_skills_screen.*


class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen),
    android.widget.SearchView.OnQueryTextListener,
    android.widget.SearchView.OnCloseListener{

    private val chosenSkillAdapter = ChosenSkillAdapter{ Id -> SkillsScreenFacade.removeData(Id);
        updateAdapter(SkillsScreenFacade.getResult());
        updateAdapterOfCS((SkillsScreenFacade.getNewData()))}

    private val SCAdapter = SkillsScreenAdapter { skillId -> SkillsScreenFacade.addData(skillId);
        updateAdapter(SkillsScreenFacade.getResult());
        updateAdapterOfCS(SkillsScreenFacade.getNewData()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSCAdapter()
        updateAdapter(SkillsScreenFacade.refreshDisplayableList())
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
            if (query!="") searchSkill(query)
        }
        searchView.clearFocus();
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            if (query!="") searchSkill(query)
        }
        return true
    }

    override fun onClose(): Boolean {
        searchView.setIconified(false);
        searchView.clearFocus();
        updateAdapter(SkillsScreenFacade.refreshDisplayableList())
        return true
    }

    private fun searchSkill(query: String){
        updateAdapter(SkillsScreenFacade.searchItem(query))
    }

    companion object {
        fun newInstance(): SkillsScreenFragment = SkillsScreenFragment()
    }
}
