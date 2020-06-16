package com.seweryn.cvapp.viewmodel


import androidx.lifecycle.ViewModel
import com.seweryn.cvapp.tools.network.error.ConnectionError
import com.seweryn.cvapp.utils.SchedulerProvider
import com.seweryn.cvapp.viewmodel.liveDataModels.Error
import com.seweryn.cvapp.viewmodel.liveDataModels.ErrorAction
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(private val schedulersProvider: SchedulerProvider) : ViewModel() {

    private val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    protected fun <T> load(
        command: Observable<T>,
        onNext: (T) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        subscriptions.add(
            command.subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler(), true)
                .subscribe(
                    { result -> onNext.invoke(result) },
                    { error -> onError.invoke(error) },
                    { onComplete.invoke() })
        )
    }

    protected fun <T> load(
        command: Flowable<T>,
        onNext: (T) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        subscriptions.add(
            command.subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler(), true)
                .subscribe(
                    { result -> onNext.invoke(result) },
                    { error -> onError.invoke(error) },
                    { onComplete.invoke() })
        )
    }

    protected fun handleError(throwable: Throwable, action: ErrorAction? = null): Error {
        return when (throwable) {
            is ConnectionError -> Error.ConnectionError(action)
            else -> Error.GenericError(action)
        }
    }
}