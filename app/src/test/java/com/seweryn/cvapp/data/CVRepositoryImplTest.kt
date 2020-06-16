package com.seweryn.cvapp.data

import com.seweryn.cvapp.data.local.CVDao
import com.seweryn.cvapp.data.local.Database
import com.seweryn.cvapp.data.model.BasicInformation
import com.seweryn.cvapp.data.model.CV
import com.seweryn.cvapp.data.remote.CVApi
import io.reactivex.Flowable
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class CVRepositoryImplTest {

    private lateinit var systemUnderTest: CVRepositoryImpl

    @Mock
    lateinit var api: CVApi

    @Mock
    lateinit var database: Database

    @Mock
    lateinit var cvDao: CVDao

    @Before
    fun setUp() {
        systemUnderTest = CVRepositoryImpl(api, database)
        mockDatabase()
    }

    @Test
    fun `should check if cv is in database before fetching from api`() {
        simulateBasicsCanBeLoadedFromDatabase()
        simulateSkillsCanBeLoadedFromDatabase()
        simulateExperienceCanBeLoadedFromDatabase()
        simulateEducationCanBeLoadedFromDatabase()
        simulateCVCanBeFetchedFromApi()

        val order = inOrder(cvDao, api)
        systemUnderTest.getCV().test()

        order.verify(cvDao).queryBasics()
        order.verify(cvDao).querySkills()
        order.verify(cvDao).queryExperience()
        order.verify(cvDao).queryEducation()
        order.verify(api).getCV()
    }

    @Test
    fun `should not publish error when failed to load from database when getting cv`() {
        simulateBasicsCannotBeLoadedFromDatabase()
        simulateSkillsCanBeLoadedFromDatabase()
        simulateExperienceCanBeLoadedFromDatabase()
        simulateEducationCanBeLoadedFromDatabase()
        simulateCVCanBeFetchedFromApi()

        val order = inOrder(cvDao, api)
        assertEquals(0, systemUnderTest.getCV().test().errorCount())
    }

    @Test
    fun `should save cv in database when fetched from api`() {
        simulateBasicsCanBeLoadedFromDatabase()
        simulateSkillsCanBeLoadedFromDatabase()
        simulateExperienceCanBeLoadedFromDatabase()
        simulateEducationCanBeLoadedFromDatabase()
        simulateCVCanBeFetchedFromApi()
        systemUnderTest.getCV().test()
        verify(cvDao).synchronizeCV(any())
    }

    @Test
    fun `should observe basics from database when requested`() {
        simulateBasicsCanBeLoadedAndObservedFromDatabase()
        systemUnderTest.getBasicInfo().test()
        verify(cvDao).observeBasics()
        verifyNoMoreInteractions(api)
    }

    @Test
    fun `should observe skills from database when requested`() {
        simulateSkillsCanBeLoadedAndObservedFromDatabase()
        systemUnderTest.getSkills().test()
        verify(cvDao).observeSkills()
        verifyNoMoreInteractions(api)
    }

    @Test
    fun `should observe education from database when requested`() {
        simulateEducationCanBeLoadedAndObservedFromDatabase()
        systemUnderTest.getEducation().test()
        verify(cvDao).observeEducation()
        verifyNoMoreInteractions(api)
    }

    @Test
    fun `should observe Experience from database when requested`() {
        simulateExperienceCanBeLoadedAndObservedFromDatabase()
        systemUnderTest.getExperience().test()
        verify(cvDao).observeExperience()
        verifyNoMoreInteractions(api)
    }

    private fun mockDatabase() {
        `when`(database.cvDao()).thenReturn(cvDao)
    }

    private fun simulateBasicsCanBeLoadedFromDatabase() {
        `when`(cvDao.queryBasics()).thenReturn(Single.just(createBasics()))
    }

    private fun simulateBasicsCanBeLoadedAndObservedFromDatabase() {
        `when`(cvDao.observeBasics()).thenReturn(Flowable.just(createBasics()))
    }

    private fun simulateBasicsCannotBeLoadedFromDatabase() {
        `when`(cvDao.queryBasics()).thenReturn(Single.error(Exception()))
    }

    private fun simulateSkillsCanBeLoadedFromDatabase() {
        `when`(cvDao.querySkills()).thenReturn(Single.just(listOf()))
    }

    private fun simulateSkillsCanBeLoadedAndObservedFromDatabase() {
        `when`(cvDao.observeSkills()).thenReturn(Flowable.just(listOf()))
    }

    private fun simulateEducationCanBeLoadedFromDatabase() {
        `when`(cvDao.queryEducation()).thenReturn(Single.just(listOf()))
    }

    private fun simulateEducationCanBeLoadedAndObservedFromDatabase() {
        `when`(cvDao.observeEducation()).thenReturn(Flowable.just(listOf()))
    }

    private fun simulateExperienceCanBeLoadedFromDatabase() {
        `when`(cvDao.queryExperience()).thenReturn(Single.just(listOf()))
    }

    private fun simulateExperienceCanBeLoadedAndObservedFromDatabase() {
        `when`(cvDao.observeExperience()).thenReturn(Flowable.just(listOf()))
    }

    private fun simulateCVCanBeFetchedFromApi() {
        `when`(api.getCV()).thenReturn(Single.just(createCV()))
    }

    private fun createCV() = CV(
        basics = createBasics(),
        skills = listOf(),
        education = listOf(),
        experience = listOf()
    )

    private fun createBasics() = BasicInformation(
        name = "name",
        surname = "surname",
        position = "position",
        email = "email",
        phone = "phone",
        photoUrl = "photo"
    )

    fun <T> any(): T = ArgumentMatchers.any<T>()
}