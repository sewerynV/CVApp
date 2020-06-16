package com.seweryn.cvapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.utils.SchedulerProvider
import com.seweryn.cvapp.viewmodel.MainViewModel
import com.seweryn.cvapp.viewmodel.sections.BasicsViewModel
import com.seweryn.cvapp.viewmodel.sections.EducationViewModel
import com.seweryn.cvapp.viewmodel.sections.ExperienceViewModel
import com.seweryn.cvapp.viewmodel.sections.SkillsViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class ViewModelModule  {

    @MapKey
    @Target(AnnotationTarget.FUNCTION)
    annotation class ViewModelKey(
        val value: KClass<out ViewModel>
    )

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(
        cvRepository: CVRepository,
        schedulerProvider: SchedulerProvider
    ): ViewModel {
        return MainViewModel(cvRepository, schedulerProvider)
    }

    @Provides
    @IntoMap
    @ViewModelKey(BasicsViewModel::class)
    fun provideBasicsViewModel(
        cvRepository: CVRepository,
        schedulerProvider: SchedulerProvider
    ): ViewModel {
        return BasicsViewModel(cvRepository, schedulerProvider)
    }

    @Provides
    @IntoMap
    @ViewModelKey(SkillsViewModel::class)
    fun provideSkillsViewModel(
        cvRepository: CVRepository,
        schedulerProvider: SchedulerProvider
    ): ViewModel {
        return SkillsViewModel(cvRepository, schedulerProvider)
    }

    @Provides
    @IntoMap
    @ViewModelKey(ExperienceViewModel::class)
    fun provideExperienceViewModel(
        cvRepository: CVRepository,
        schedulerProvider: SchedulerProvider
    ): ViewModel {
        return ExperienceViewModel(cvRepository, schedulerProvider)
    }

    @Provides
    @IntoMap
    @ViewModelKey(EducationViewModel::class)
    fun provideEducationViewModel(
        cvRepository: CVRepository,
        schedulerProvider: SchedulerProvider
    ): ViewModel {
        return EducationViewModel(cvRepository, schedulerProvider)
    }

    @Provides
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
        }
    }

}