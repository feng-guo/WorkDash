package com.example.workdash.models

data class EmployerProfileModel(
    var uid: String,
    var isWorker: Boolean,
    var name: String,
    var email: String,
    var phone: String,
    var address: String,
    var salary: Int,
    var isVerified: Boolean,
    var workDays: List<String>,
    var startTime: String,
    var endTime: String,
    var selectedId: String
) {
    constructor() : this("", true, "", "", "", "", 0, false, listOf(), "", "", "")
}
