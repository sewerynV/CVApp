package com.seweryn.cvapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import com.seweryn.cvapp.data.model.Skill
import com.seweryn.cvapp.ui.list.skill.SkillsAdapter
import com.seweryn.cvapp.viewmodel.sections.SkillsViewModel

class SkillsFragment : CVListSectionFragment<Skill>() {

    companion object {
        val TAG = "skills"
    }

    override fun viewModel() =
        ViewModelProvider(this, viewModelFactory).get(SkillsViewModel::class.java)

    override val contentAdapter = SkillsAdapter()

    override fun updateContent(content: List<Skill>) {
        contentAdapter.updateSkills(content)
    }

}