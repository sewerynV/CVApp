package com.seweryn.cvapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.data.model.BasicInformation
import com.seweryn.cvapp.data.model.CV
import com.seweryn.cvapp.utils.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var cvRepository: CVRepository

    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    @Mock
    lateinit var progressListener: (Boolean) -> Unit

    @Before
    fun setUp() {
        mockSchedulers()
    }

    @Test
    fun `should get cv on initialisation`() {
        simulateCvCanBeLoaded()
        MainViewModel(cvRepository, schedulerProvider)
        verify(cvRepository).getCV()
    }

    @Test
    fun `should update state when cv is loaded`() {
        simulateCvCanBeLoaded()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        systemUnderTest.initializationState.observeForever { }
        assertNotNull(systemUnderTest.initializationState.value)
        assertEquals(
            MainViewModel.InitializationState.Ready,
            systemUnderTest.initializationState.value
        )
    }

    @Test
    fun `should update state when cv cannot be loaded`() {
        simulateCvCannotBeLoaded()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        systemUnderTest.initializationState.observeForever { }
        assertNotNull(systemUnderTest.initializationState.value)
        assertThat(
            systemUnderTest.initializationState.value,
            CoreMatchers.instanceOf(MainViewModel.InitializationState.Failure::class.java)
        )
    }

    @Test
    fun `should show result and not error when loaded cv but later received error`() {
        simulateCvCanBeLoadedFromCacheButNotFromApi()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        systemUnderTest.initializationState.observeForever { }
        assertNotNull(systemUnderTest.initializationState.value)
        assertThat(
            systemUnderTest.initializationState.value,
            CoreMatchers.instanceOf(MainViewModel.InitializationState.Ready::class.java)
        )
    }

    @Test
    fun `should reload cv when selected retry option from error message`() {
        simulateCvCannotBeLoaded()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        (systemUnderTest.initializationState.value as MainViewModel.InitializationState.Failure).error.errorAction?.action?.invoke()
        verify(cvRepository, Mockito.times(2)).getCV()
    }

    @Test
    fun `should show progress when trying to load cv`() {
        simulateCvCannotBeLoaded()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        systemUnderTest.initializationState.observeForever {
            if (it is MainViewModel.InitializationState.Progress) progressListener.invoke(true)
        }
        simulateCvCanBeLoaded()
        (systemUnderTest.initializationState.value as MainViewModel.InitializationState.Failure).error.errorAction?.action?.invoke()
        verify(progressListener).invoke(true)
    }

    @Test
    fun `should show secondary progress when trying to load cv`() {
        simulateCvCannotBeLoaded()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        systemUnderTest.secondaryProgress.observeForever {
            progressListener.invoke(it)
        }
        simulateCvCanBeLoaded()
        (systemUnderTest.initializationState.value as MainViewModel.InitializationState.Failure).error.errorAction?.action?.invoke()
        verify(progressListener).invoke(true)
    }

    @Test
    fun `should hide secondary progress when cv loaded`() {
        simulateCvCannotBeLoaded()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        systemUnderTest.secondaryProgress.observeForever {
            progressListener.invoke(it)
        }
        simulateCvCanBeLoaded()
        (systemUnderTest.initializationState.value as MainViewModel.InitializationState.Failure).error.errorAction?.action?.invoke()
        verify(progressListener, times(2)).invoke(false)
    }

    @Test
    fun `should hide secondary progress when cv failed to load`() {
        simulateCvCannotBeLoaded()
        val systemUnderTest = MainViewModel(cvRepository, schedulerProvider)
        systemUnderTest.secondaryProgress.observeForever {
            progressListener.invoke(it)
        }
        (systemUnderTest.initializationState.value as MainViewModel.InitializationState.Failure).error.errorAction?.action?.invoke()
        verify(progressListener, times(2)).invoke(false)
    }

    private fun mockSchedulers() {
        `when`(schedulerProvider.ioScheduler()).thenReturn(Schedulers.trampoline())
        `when`(schedulerProvider.uiScheduler()).thenReturn(Schedulers.trampoline())
    }

    private fun simulateCvCanBeLoaded() {
        `when`(cvRepository.getCV()).thenReturn(Observable.just(createCV()))
    }

    private fun simulateCvCannotBeLoaded() {
        `when`(cvRepository.getCV()).thenReturn(Observable.error(Exception()))
    }

    private fun simulateCvCanBeLoadedFromCacheButNotFromApi() {
        `when`(cvRepository.getCV()).thenReturn(
            Observable.concat(
                Observable.just(createCV()),
                Observable.error(Exception())
            )
        )
    }

    private fun createCV() = CV(
        basics = BasicInformation(
            name = "name",
            surname = "surname",
            position = "position",
            email = "email",
            phone = "phone",
            photoUrl = "photo"
        ),
        skills = listOf(),
        education = listOf(),
        experience = listOf()
    )


}