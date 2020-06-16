package com.seweryn.cvapp.viewmodel.sections

import androidx.lifecycle.MutableLiveData
import com.seweryn.cvapp.utils.SchedulerProvider
import com.seweryn.cvapp.viewmodel.BaseViewModel
import com.seweryn.cvapp.viewmodel.liveDataModels.Error
import com.seweryn.cvapp.viewmodel.liveDataModels.ErrorAction
import io.reactivex.Flowable

abstract class CVSectionViewModel<CONTENT>(schedulerProvider: SchedulerProvider) :
    BaseViewModel(schedulerProvider) {

    val content = MutableLiveData<CONTENT>()
    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<Error?>()

    protected fun getData(contentCommand: Flowable<CONTENT>) {
        progress.value = true
        error.value = null
        load(
            command = contentCommand,
            onNext = { result ->
                content.value = result
                progress.value = false
            },
            onComplete = {
                progress.value = false
            },
            onError = { error ->
                progress.value = false
                this.error.value = handleError(error, ErrorAction.Retry { getData(contentCommand) })
            }
        )
    }
}