package com.seweryn.cvapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seweryn.cvapp.data.model.BasicInformation
import com.seweryn.cvapp.data.model.Education
import com.seweryn.cvapp.data.model.Experience
import com.seweryn.cvapp.data.model.Skill

@Database(
    entities = [BasicInformation::class, Experience::class, Education::class, Skill::class],
    version = 3,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun cvDao(): CVDao
}