package com.example.workdash.models

data class JobApplicationModel(
    var jobApplicationId: String,
    var jobId: String,
    var employeeId: String,
    var applicationStatus: String
) {
    constructor() : this("", "", "", "") {}
}
