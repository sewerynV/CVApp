package com.seweryn.cvapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Experience(
    @PrimaryKey val id: Int,
    val companyName: String,
    val companyIconUrl: String,
    val position: String,
    val startDate: String,
    val endDate: String,
    val description: String?
)