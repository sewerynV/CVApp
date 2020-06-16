package com.seweryn.cvapp.utils

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ioScheduler(): Scheduler

    fun uiScheduler(): Scheduler
}