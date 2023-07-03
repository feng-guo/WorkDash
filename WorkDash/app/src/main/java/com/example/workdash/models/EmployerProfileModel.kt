package com.example.workdash.models

data class EmployerProfileModel(
    val uid: String,
    val isWorker: Boolean,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val isVerified: Boolean
)