package com.seweryn.cvapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skill(
    @PrimaryKey val id: Int,
    val name: String,
    val proficiency: Int
)