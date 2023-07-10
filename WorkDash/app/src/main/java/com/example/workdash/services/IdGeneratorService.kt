package com.example.workdash.services

import com.example.workdash.Constants.TableNames.ID_TABLE_NAME


const val locationId = "locationId"
const val jobId = "jobId"
const val jobApplicationId = "jobApplicationId"
object IdGeneratorService {

//    fun generateBusinessId(): String {
//        return ""
//    }

    private fun retrieveId(id: String, callback: (obj: String) -> Unit) {
        val tmp: Long = 0
        DatabaseService.readSingleValueFromDbTableWithId(ID_TABLE_NAME, id, tmp, callback)
    }

    private fun updateId(id: String, value: Long) {
        DatabaseService.writeToDbTable(ID_TABLE_NAME, id, value)
    }

    //TODO note these arent atomic so... I'll figure it out later

    private fun generateId(idName: String, callback: (obj: String) -> Unit) {
        var id: String
        val lmd = { retrievedId : String ->
            id = retrievedId
            updateId(idName, id.toLong()+1)
            callback.invoke(id)
        }
        retrieveId(idName, lmd)
    }

    fun generateLocationId(callback: (obj: String) -> Unit) {
        generateId(locationId, callback)
    }

    fun generateJobId(callback: (obj: String) -> Unit) {
        generateId(jobId, callback)
    }

    fun generateJobApplicationId(callback: (obj: String) -> Unit) {
        generateId(jobApplicationId, callback)
    }
}