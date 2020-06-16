package com.seweryn.cvapp.viewmodel.sections

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.data.model.Skill
import com.seweryn.cvapp.utils.SchedulerProvider
import com.seweryn.cvapp.viewmodel.liveDataModels.Error
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class CVSectionViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var systemUnderTest: CVSectionViewModelImpl

    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    @Mock
    lateinit var cvRepository: CVRepository

    @Mock
    lateinit var progressListener: (Boolean) -> Unit

    @Before
    fun setUp() {
        systemUnderTest = CVSectionViewModelImpl(schedulerProvider)
        mockSchedulers()
    }

    @Test
    fun `should get content when requested`() {
        simulateContentCanBeLoaded()
        systemUnderTest.test()
        Mockito.verify(cvRepository).getSkills()
    }

    @Test
    fun `should update content when it is loaded`() {
        simulateContentCanBeLoaded()
        systemUnderTest.content.observeForever { }
        systemUnderTest.test()
        assertNotNull(systemUnderTest.content.value)
        assertEquals(
            1,
            systemUnderTest.content.value?.size
        )
    }

    @Test
    fun `should show error when failed to load content`() {
        simulateContentCannotBeLoaded()
        systemUnderTest.content.observeForever { }
        systemUnderTest.test()
        assertNotNull(systemUnderTest.error.value)
        assertThat(
            systemUnderTest.error.value,
            CoreMatchers.instanceOf(Error.GenericError::class.java)
        )
    }

    @Test
    fun `should hide error when loading content`() {
        simulateContentCannotBeLoaded()
        systemUnderTest.content.observeForever { }
        systemUnderTest.test()
        simulateContentCanBeLoaded()
        systemUnderTest.test()
        assertNull(systemUnderTest.error.value)
    }

    @Test
    fun `should show progress when trying to load content`() {
        simulateContentCanBeLoaded()
        systemUnderTest.progress.observeForever {
            progressListener.invoke(it)
        }
        systemUnderTest.test()
        Mockito.verify(progressListener).invoke(true)
    }

    @Test
    fun `should hide progress when content loaded`() {
        simulateContentCanBeLoaded()
        systemUnderTest.progress.observeForever {
            progressListener.invoke(it)
        }
        systemUnderTest.test()
        Mockito.verify(progressListener, Mockito.times(2)).invoke(false)
    }

    @Test
    fun `should hide progress when content failed to load`() {
        simulateContentCannotBeLoaded()
        systemUnderTest.progress.observeForever {
            progressListener.invoke(it)
        }
        systemUnderTest.test()
        Mockito.verify(progressListener).invoke(false)
    }

    private fun mockSchedulers() {
        Mockito.`when`(schedulerProvider.ioScheduler()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulerProvider.uiScheduler()).thenReturn(Schedulers.trampoline())
    }

    private fun simulateContentCanBeLoaded() {
        Mockito.`when`(cvRepository.getSkills()).thenReturn(Flowable.just(listOf(createSkill())))
    }

    private fun simulateContentCannotBeLoaded() {
        Mockito.`when`(cvRepository.getSkills()).thenReturn(Flowable.error(Exception()))
    }

    private fun createSkill() = Skill(
        id = 1,
        name = "name",
        proficiency = 2
    )

    inner class CVSectionViewModelImpl(schedulerProvider: SchedulerProvider) :
        CVSectionViewModel<List<Skill>>(schedulerProvider) {

        fun test() {
            getData(cvRepository.getSkills())
        }
    }
}