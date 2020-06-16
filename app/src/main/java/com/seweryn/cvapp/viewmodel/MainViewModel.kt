package com.seweryn.cvapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.utils.SchedulerProvider
import com.seweryn.cvapp.viewmodel.liveDataModels.Error
import com.seweryn.cvapp.viewmodel.liveDataModels.ErrorAction
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cvRepository: CVRepository,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    var initializationState: MutableLiveData<InitializationState> = MutableLiveData()
    var secondaryProgress: MutableLiveData<Boolean> = MutableLiveData()

    init {
        initialize()
    }

    private fun initialize() {
        initializationState.value = InitializationState.Progress
        secondaryProgress.value = true
        load(
            command = cvRepository.getCV(),
            onNext = {
                initializationState.value = InitializationState.Ready
            },
            onError = {
                secondaryProgress.value = false
                if (shouldShowInitializationError()) {
                    initializationState.value =
                        InitializationState.Failure(
                            handleError(
                                it,
                                ErrorAction.Retry { initialize() })
                        )
                }
            },
            onComplete = {
                secondaryProgress.value = false
            }
        )
    }

    private fun shouldShowInitializationError() =
        initializationState.value !is InitializationState.Ready

    sealed class InitializationState {
        object Progress : InitializationState()
        object Ready : InitializationState()
        class Failure(val error: Error) : InitializationState()
    }
}