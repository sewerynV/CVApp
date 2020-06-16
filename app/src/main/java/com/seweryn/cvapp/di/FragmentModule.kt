package com.seweryn.cvapp.di

import com.seweryn.cvapp.ui.fragments.BasicsFragment
import com.seweryn.cvapp.ui.fragments.EducationFragment
import com.seweryn.cvapp.ui.fragments.ExperienceFragment
import com.seweryn.cvapp.ui.fragments.SkillsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeBasicsFragment(): BasicsFragment

    @ContributesAndroidInjector
    abstract fun contributeSkillsFragment(): SkillsFragment

    @ContributesAndroidInjector
    abstract fun contributeExperienceFragment(): ExperienceFragment

    @ContributesAndroidInjector
    abstract fun contributeEducationFragment(): EducationFragment
}