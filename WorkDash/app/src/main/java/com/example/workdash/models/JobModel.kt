package com.example.workdash.models

data class JobModel(
    var locationId: String,
    var jobId: String,

    var jobName: String,
    var jobState: String,
    var schedule: String,
    var payPerHour: Long,
    var certificationsRequired: String,
    var totalPositionsRequired: Long,
    var totalPositionsFilled: Long

) {
    constructor() : this("", "", "", "", "", 0, "", 0, 0)
    constructor(locationId: String, jobId: String, jobName: String, schedule: String, payPerHour: Long, certificationsRequired: String, totalPositionsRequired: Long) : this(locationId, jobId, jobName, "Pending", schedule, payPerHour, certificationsRequired, totalPositionsRequired, 0)
}