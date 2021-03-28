package com.android.opus.ui.screen.skillscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.opus.R
import kotlinx.android.synthetic.main.activity_skills_screen.*

class SkillsScreenFragment : Fragment(R.layout.activity_skills_screen){

    private lateinit var viewModel: SkillsScreenViewModel
    private lateinit var skillsScreenAdapter: SkillsScreenAdapter
    var res = resources

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.activity_skills_screen, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            offeredSkills.apply {
                val list = ArrayList<String>()
                list.add("sad")
                val data = res?.getStringArray(R.array.skills_arr)
                list.addAll(data)
                skillsScreenAdapter = SkillsScreenAdapter(list)
                offeredSkills?.layoutManager = GridLayoutManager(requireContext(), 4)
                offeredSkills?.adapter = skillsScreenAdapter
            }
        }


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        viewModel = SkillsScreenViewModel(
//                SkillsScreenInteractor(
//                        dispatcher = Dispatchers.Default,
//                        context = context
//                )
//        )
//    }

//    fun setUpSkillsScreenAdapter(){
//        var data = res.getStringArray(R.array.skills_arr)
//        list.addAll(data);
//        skillsScreenAdapter = SkillsScreenAdapter(list)
//        offeredSkills?.layoutManager = GridLayoutManager(requireContext(),4)
//        offeredSkills?.adapter = skillsScreenAdapter
//    }

    companion object {
        fun newInstance(): SkillsScreenFragment = SkillsScreenFragment()
    }
}