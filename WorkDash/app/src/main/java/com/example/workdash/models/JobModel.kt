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
    val isFirstJob: Boolean, //might delete later
    val location: LocationModel

//    val locationId: String,
//    val jobId: String,
//
//    val jobName: String,
//    val jobState: String,
//    val schedule: String, //This might have to be some sort of timeobject instead
//    val payPerHour: Int,
//    val totalCompensation: Int, //This might just be calculated on render instead of being stored
//    val certificationsRequired: List<Certifications>,
//    val totalPositionsRequired: Int,
//    val totalPositionsFilled: Int

)