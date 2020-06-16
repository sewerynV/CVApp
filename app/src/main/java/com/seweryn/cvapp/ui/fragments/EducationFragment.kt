package com.seweryn.cvapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import com.seweryn.cvapp.data.model.Education
import com.seweryn.cvapp.ui.list.education.EducationAdapter
import com.seweryn.cvapp.viewmodel.sections.EducationViewModel

class EducationFragment : CVListSectionFragment<Education>() {

    companion object {
        val TAG = "education"
    }

    override fun viewModel() =
        ViewModelProvider(this, viewModelFactory).get(EducationViewModel::class.java)

    override val contentAdapter = EducationAdapter()

    override fun updateContent(content: List<Education>) {
        contentAdapter.setEducation(content)
    }

}