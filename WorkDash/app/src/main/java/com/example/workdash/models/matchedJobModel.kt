package com.example.workdash.models

data class matchedJobModel(
    var jobId: String,
    var employeeId: String,
    var checkInState: String,
    var checkOutState: String,
    var checkInTime: String,
    var checkOutTime: String
) {
    constructor() : this("", "", "False", "False", "", "")

//    constructor(jobId: String, businessId: String, workerId: String, employeeId: String) : this(jobId, businessId, workerId, employeeId)

}

