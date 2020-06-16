package com.seweryn.cvapp.data.remote

import com.seweryn.cvapp.data.model.CV
import io.reactivex.Single
import retrofit2.http.GET

interface CVApi {
    @GET("b4712d44657ff2fb136c79ada831a4c5/raw/")
    fun getCV(): Single<CV>
}