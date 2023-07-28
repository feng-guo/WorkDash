package com.example.workdash.models

data class MatchedJobModel(
    var jobId: String,
    var employeeId: String,
    var checkInState: String,
    var checkOutState: String,
    var checkInTime: String,
    var checkOutTime: String,
    var jobId_employeeId: String
) {
    constructor() : this("", "", "false", "false", "", "", "")

//    constructor(jobId: String, businessId: String, workerId: String, employeeId: String) : this(jobId, businessId, workerId, employeeId)

}

