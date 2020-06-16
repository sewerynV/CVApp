package com.seweryn.cvapp.viewmodel.sections

import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.data.model.Skill
import com.seweryn.cvapp.utils.SchedulerProvider

class SkillsViewModel(
    cvRepository: CVRepository,
    schedulerProvider: SchedulerProvider
) : CVSectionViewModel<List<Skill>>(schedulerProvider) {
    init {
        getData(cvRepository.getSkills())
    }
}