package com.seweryn.cvapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Education(
    @PrimaryKey val id: Int,
    val schoolName: String,
    val schoolIconUrl: String,
    val degree: String?,
    val specialization: String?,
    val startDate: String,
    val endDate: String
)