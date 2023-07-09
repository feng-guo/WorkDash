package com.example.workdash.services

import com.example.workdash.Constants.TableNames.ID_TABLE_NAME


const val locationId = "locationId"
const val jobId = "jobId"
const val jobApplicationId = "jobApplicationId"
object IdGeneratorService {

//    fun generateBusinessId(): String {
//        return ""
//    }

    private fun retrieveId(id: String): String {
        return DatabaseService.readSingleValueFromDbTableWithId(ID_TABLE_NAME, id)
    }

    private fun updateId(id: String, value: String) {
        DatabaseService.writeToDbTable(ID_TABLE_NAME, id, value)
    }

    //TODO note these arent atomic so... I'll figure it out later
    fun generateLocationId(): String {
        val id = retrieveId(locationId)
        updateId(locationId, (id.toLong()+1).toString())
        return id
    }

    fun generateJobId(): String {
        val id = retrieveId(jobId)
        updateId(jobId, (id.toLong()+1).toString())
        return id
    }

    fun generateJobApplicationId(): String {
        val id = retrieveId(jobApplicationId)
        updateId(jobApplicationId, (id.toLong()+1).toString())
        return id
    }
}