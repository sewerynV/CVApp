package com.seweryn.cvapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BasicInformation(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val surname: String,
    val position: String,
    val email: String,
    val phone: String,
    val photoUrl: String
)