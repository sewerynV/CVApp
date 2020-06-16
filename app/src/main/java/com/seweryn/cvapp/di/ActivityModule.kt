package com.seweryn.cvapp.di

import com.seweryn.cvapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule  {
    @ContributesAndroidInjector
    abstract fun contributeEventsFragment(): MainActivity
}