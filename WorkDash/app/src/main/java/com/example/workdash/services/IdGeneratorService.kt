package com.example.workdash.services

import com.example.workdash.Constants.TableNames.ID_TABLE_NAME



object IdGeneratorService {
    private const val locationId = "locationId"
    private const val jobId = "jobId"
    private const val jobApplicationId = "jobApplicationId"
    private const val reportId = "reportId"

    private fun retrieveId(id: String, callback: (obj: Long) -> Unit) {
        DatabaseService.readIdValueFromIdTable(ID_TABLE_NAME, id, callback)
    }

    private fun updateId(id: String, value: Long) {
        DatabaseService.writeToDbTable(ID_TABLE_NAME, id, value)
    }

    private fun generateId(idName: String, callback: (obj: Long) -> Unit) {
        var id: Long
        val lmd = { retrievedId : Long ->
            id = retrievedId
            updateId(idName, id+1)
            callback.invoke(id)
        }
        retrieveId(idName, lmd)
    }

    fun generateLocationId(callback: (obj: Long) -> Unit) {
        generateId(locationId, callback)
    }

    fun generateJobId(callback: (obj: Long) -> Unit) {
        generateId(jobId, callback)
    }

    fun generateJobApplicationId(callback: (obj: Long) -> Unit) {
        generateId(jobApplicationId, callback)
    }

    fun generateReportId(callback: (obj: Long) -> Unit) {
        generateId(reportId, callback)
    }
}