package com.seweryn.cvapp.data

import com.seweryn.cvapp.data.model.*
import io.reactivex.Flowable
import io.reactivex.Observable

interface CVRepository {
    fun getCV(): Observable<CV>
    fun getBasicInfo(): Flowable<BasicInformation>
    fun getSkills(): Flowable<List<Skill>>
    fun getExperience(): Flowable<List<Experience>>
    fun getEducation(): Flowable<List<Education>>
}