package com.seweryn.cvapp.viewmodel.sections

import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.data.model.BasicInformation
import com.seweryn.cvapp.utils.SchedulerProvider

class BasicsViewModel(
    cvRepository: CVRepository,
    schedulerProvider: SchedulerProvider
) : CVSectionViewModel<BasicInformation>(schedulerProvider) {
    init {
        getData(cvRepository.getBasicInfo())
    }
}