package com.seweryn.cvapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import com.seweryn.cvapp.data.model.Experience
import com.seweryn.cvapp.ui.list.experience.ExperienceAdapter
import com.seweryn.cvapp.viewmodel.sections.ExperienceViewModel

class ExperienceFragment : CVListSectionFragment<Experience>() {

    companion object {
        val TAG = "experience"
    }

    override fun viewModel() =
        ViewModelProvider(this, viewModelFactory).get(ExperienceViewModel::class.java)

    override val contentAdapter = ExperienceAdapter()

    override fun updateContent(content: List<Experience>) {
        contentAdapter.setExperience(content)
    }

}