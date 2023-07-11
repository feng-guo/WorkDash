package com.example.workdash.models

data class JobApplicationModel(
    var jobApplicationId: String,
    var jobId: String,
    var employeeId: String,
    var applicationStatus: String
) {
    constructor() : this("", "", "", "")

    constructor(jobApplicationId: String, jobId: String, employeeId: String) : this(jobApplicationId, jobId, employeeId, "Pending")
}
