package com.seweryn.cvapp.data

import com.seweryn.cvapp.data.local.Database
import com.seweryn.cvapp.data.model.*
import com.seweryn.cvapp.data.remote.CVApi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function4

class CVRepositoryImpl(private val cvApi: CVApi, private val database: Database) : CVRepository {

    override fun getCV(): Observable<CV> {
        return Observable.concat(
            queryCV().toObservable().onErrorResumeNext(Observable.empty()),
            cvApi.getCV().doOnSuccess {
                database.cvDao().synchronizeCV(it)
            }.toObservable()
        )
    }

    override fun getBasicInfo(): Flowable<BasicInformation> {
        return database.cvDao().observeBasics()
    }

    override fun getSkills(): Flowable<List<Skill>> {
        return database.cvDao().observeSkills()
    }

    override fun getExperience(): Flowable<List<Experience>> {
        return database.cvDao().observeExperience()
    }

    override fun getEducation(): Flowable<List<Education>> {
        return database.cvDao().observeEducation()
    }

    private fun queryCV(): Single<CV> {
        return Single.zip(
            database.cvDao().queryBasics(),
            database.cvDao().querySkills(),
            database.cvDao().queryExperience(),
            database.cvDao().queryEducation(),
            Function4<BasicInformation, List<Skill>, List<Experience>, List<Education>, CV>
            { basics, skills, experience, education ->
                CV(basics, skills, experience, education)
            }
        )
    }

}