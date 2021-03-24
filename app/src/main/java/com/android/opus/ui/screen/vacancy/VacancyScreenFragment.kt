package com.android.opus.ui.screen.vacancy

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.opus.R
import com.android.opus.common.DefaultItemDecorator
import com.android.opus.domain.VacancyInteractor
import com.android.opus.model.Vacancy
import kotlinx.android.synthetic.main.fragment_vacanies.*
import kotlinx.coroutines.Dispatchers

class VacancyScreenFragment : Fragment(R.layout.fragment_vacanies) {
    private var viewModel = VacancyScreenViewModel(
        VacancyInteractor(dispatcher = Dispatchers.Default)
    )

    private var listener: VacancyItemClickListener? = null
    private val vacancyListAdapter: VacancyDelegateAdapter? = VacancyDelegateAdapter { vacancyId ->
        listener?.onVacancySelected(vacancyId = vacancyId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is VacancyItemClickListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpVacancyAdapter()
        viewModel.vacancies.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    private fun setUpVacancyAdapter() {
        vacancies?.layoutManager = LinearLayoutManager(requireContext())
        vacancies?.addItemDecoration(DefaultItemDecorator(50))
        vacancies?.adapter = vacancyListAdapter
    }

    private fun updateAdapter(vacancies: List<Vacancy>?) {
        vacancies?.let { vacancyListAdapter?.setData(it) }
    }

    interface VacancyItemClickListener {
        fun onVacancySelected(vacancyId: Int)
    }

    companion object {
        fun newInstance(): VacancyScreenFragment = VacancyScreenFragment()
    }
}
