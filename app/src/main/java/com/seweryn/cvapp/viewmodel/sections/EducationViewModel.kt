package com.seweryn.cvapp.viewmodel.sections

import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.data.model.Education
import com.seweryn.cvapp.utils.SchedulerProvider

class EducationViewModel(
    cvRepository: CVRepository,
    schedulerProvider: SchedulerProvider
) : CVSectionViewModel<List<Education>>(schedulerProvider) {
    init {
        getData(cvRepository.getEducation())
    }
}