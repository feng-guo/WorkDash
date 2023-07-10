package com.example.workdash.models

data class JobModel(
    var locationId: String,
    var jobId: String,

    var jobName: String,
    var jobState: String,
    var schedule: String, //This might have to be some sort of timeobject instead
    var payPerHour: Long,
//    val totalCompensation: Int, //This might just be calculated on render instead of being stored
    //val certificationsRequired: List<Certifications>,
    //TODO convert certifications to certification object
    var certificationsRequired: String,
    var totalPositionsRequired: Long,
    var totalPositionsFilled: Long

) {
    constructor() : this("", "", "", "", "", 0, "", 0, 0)

    //TODO change pending to a constant
    constructor(locationId: String, jobId: String, jobName: String, schedule: String, payPerHour: Long, certificationsRequired: String, totalPositionsRequired: Long) : this(locationId, jobId, jobName, "Pending", schedule, payPerHour, certificationsRequired, totalPositionsRequired, 0)
}