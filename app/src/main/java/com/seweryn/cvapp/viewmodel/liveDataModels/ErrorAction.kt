package com.seweryn.cvapp.viewmodel.liveDataModels

sealed class ErrorAction(val action: () -> Unit) {
    class Retry(action: () -> Unit) : ErrorAction(action)
}