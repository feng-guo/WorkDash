package com.example.workdash.models

data class JobModel(
    val jobID: Int,
    val position: String,
    val position_for_employer: String,
    val employerName_for_employee: String,
    val pay_for_employee: String,
    val employerName: String,
    val currentState: String,
    val schedule: String,
    val pay: Int,
    val requirements: String,
    val totalPositionNumber: Int,
    val filledPositionNumber:Int,
    val location: LocationModel
)