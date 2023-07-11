package com.example.workdash.models

data class WorkerProfileModel(
    val uid: String,
    val isWorker: Boolean,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val salary: Int,
    val isVerified: Boolean,
    val workDays: List<String>,
    val startTime: String,
    val endTime: String
)