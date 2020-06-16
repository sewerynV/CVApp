package com.seweryn.cvapp.data.model

data class CV(
    val basics: BasicInformation,
    val skills: List<Skill>,
    val experience: List<Experience>,
    val education: List<Education>
)