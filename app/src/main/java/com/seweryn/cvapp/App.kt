package com.seweryn.cvapp

import android.app.Application
import com.seweryn.cvapp.di.ApplicationModule
import com.seweryn.cvapp.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = injector

}