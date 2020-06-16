package com.seweryn.cvapp.viewmodel.sections

import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.data.model.Experience
import com.seweryn.cvapp.utils.SchedulerProvider

class ExperienceViewModel(
    cvRepository: CVRepository,
    schedulerProvider: SchedulerProvider
) : CVSectionViewModel<List<Experience>>(schedulerProvider) {
    init {
        getData(cvRepository.getExperience())
    }
}