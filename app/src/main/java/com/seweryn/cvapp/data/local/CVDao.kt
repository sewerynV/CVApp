package com.seweryn.cvapp.data.local

import androidx.room.*
import com.seweryn.cvapp.data.model.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CVDao {
    @Query("SELECT * FROM basicinformation")
    fun observeBasics(): Flowable<BasicInformation>

    @Query("SELECT * FROM basicinformation")
    fun queryBasics(): Single<BasicInformation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBasics(basics: BasicInformation)

    @Query("DELETE FROM basicinformation")
    fun clearBasics()

    @Query("SELECT * FROM education")
    fun observeEducation(): Flowable<List<Education>>

    @Query("SELECT * FROM education")
    fun queryEducation(): Single<List<Education>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEducation(education: List<Education>)

    @Query("DELETE FROM education")
    fun clearEducation()

    @Query("SELECT * FROM experience")
    fun observeExperience(): Flowable<List<Experience>>

    @Query("SELECT * FROM experience")
    fun queryExperience(): Single<List<Experience>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExperience(experience: List<Experience>)

    @Query("DELETE FROM experience")
    fun clearExperience()

    @Query("SELECT * FROM skill")
    fun observeSkills(): Flowable<List<Skill>>

    @Query("SELECT * FROM skill")
    fun querySkills(): Single<List<Skill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkills(skills: List<Skill>)

    @Query("DELETE FROM skill")
    fun clearSkills()

    @Transaction
    fun synchronizeCV(cv: CV) {
        clearBasics()
        clearSkills()
        clearExperience()
        clearEducation()

        insertBasics(cv.basics)
        insertEducation(cv.education)
        insertExperience(cv.experience)
        insertSkills(cv.skills)
    }
}