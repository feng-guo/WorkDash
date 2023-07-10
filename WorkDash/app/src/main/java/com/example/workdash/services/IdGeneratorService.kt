package com.example.workdash.services

import com.example.workdash.Constants.TableNames.ID_TABLE_NAME


const val locationId = "locationId"
const val jobId = "jobId"
const val jobApplicationId = "jobApplicationId"
object IdGeneratorService {

//    fun generateBusinessId(): String {
//        return ""
//    }

    private fun retrieveId(id: String, callback: (obj: Long) -> Unit) {
        DatabaseService.readIdValueFromIdTable(ID_TABLE_NAME, id, callback)
    }

    private fun updateId(id: String, value: Long) {
        DatabaseService.writeToDbTable(ID_TABLE_NAME, id, value)
    }

    //TODO note these arent atomic so... I'll figure it out later

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
}