package com.example.workdash.models

data class ReportModel(
    var reportId: String,
    var fromUserId: String,
    var toUserId: String,
    var description: String
) {
    constructor() : this("", "", "", "")
}
