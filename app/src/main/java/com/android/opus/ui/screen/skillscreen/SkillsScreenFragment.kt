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
import com.android.opus.model.SkillsScreenField
import kotlinx.android.synthetic.main.activity_skills_screen.*
import kotlinx.coroutines.Dispatchers

class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen) {

    private lateinit var viewModel : SkillsScreenViewModel
    private lateinit var newViewModel :ChosenSkillViewModel
    //private var listenerSkillClick: SCClickListener? = null

//    private val SCAdapter: SkillsScreenAdapter? = SkillsScreenAdapter { skillId ->
//        listenerSkillClick?.ChosenSkill(skillId = skillId)}

    private val chosenSkillAdapter = ChosenSkillAdapter{Id -> SkillsScreenMockData.removeData(Id);updateAdapterOfCS((SkillsScreenMockData.getNewData()))}
    private val SCAdapter: SkillsScreenAdapter? = SkillsScreenAdapter { skillId -> SkillsScreenMockData.addData(skillId);updateAdapterOfCS(SkillsScreenMockData.getNewData()) }
    //private val SCAdapter: SkillsScreenAdapter? = SkillsScreenAdapter { skillId -> chosenSkillAdapter.(SkillsScreenMockData.getElem(skillId)) }
    //private val chosenSkillAdapter: ChosenSkillAdapter? = ChosenSkillAdapter { itemId -> SkillsScreenMockData.removeData(itemId)}

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

    private fun updateAdapter(list: List<SkillsScreenField>?) {
        SCAdapter?.submitList(list)
    }

    private fun updateAdapterOfCS(list: List<SkillsScreenField>?){
//        chosenSkillAdapter?.submitList(null);
//        chosenSkillAdapter?.submitList(list)
        chosenSkillAdapter?.submitList(list?.let { ArrayList(it) })
    }

//    interface SCClickListener {
//        fun ChosenSkill(item: Int)
//    }

    companion object {
        fun newInstance(): SkillsScreenFragment = SkillsScreenFragment()
    }
}
