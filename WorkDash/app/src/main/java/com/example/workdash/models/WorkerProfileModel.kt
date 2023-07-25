package com.example.workdash.models

data class WorkerProfileModel(
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
    var selectedId: String,
    var profilePic: String,
    var idPic: String
) {
    constructor() : this("", true, "", "", "", "", 0, false, listOf(), "", "", "", "", "")

    //constructor(uid: String, isWorker: Boolean, name: String, email: String, phone: String, address: String, salary: Int, isVerified: Boolean, workDays: List<String>, startTime: String, endTime: String, selectedId: String) : this(uid, isWorker, name, email, phone, address, salary, isVerified, workDays, startTime, endTime, selectedId)
}
