package com.example.workdash.services

import com.example.workdash.models.AddressModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object DatabaseService {
    private val dbRef = FirebaseDatabase.getInstance().reference

    fun writeToDbTable(tableName: String, key: String, value: Any) {
        dbRef.child(tableName).child(key).setValue(value)
    }

//    fun <T> readListFromDbTable(tableName: String, list: List<T>): List<T> {
//        val entriesList = dbRef.child(tableName)
//
//        val listener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    //TODO filter jobs based on filters
//                    val job =
//                    val jobObj = snapshot.value as HashMap<*, *>
//                    with(job) {
//                        locationId = jobObj["locationId"].toString()
//                        jobId = jobObj["jobId"].toString()
//                        jobName = jobObj["jobName"].toString()
//                        jobState = jobObj["jobState"].toString()
//                        schedule = jobObj["locationId"].toString()
//                        payPerHour = jobObj["payPerHour"] as Long
//                        certificationsRequired = jobObj["certificationsRequired"].toString()
//                        totalPositionsRequired = jobObj["totalPositionsRequired"] as Long
//                        totalPositionsFilled = jobObj["totalPositionsFilled"] as Long
//                    }
//                    jobs.add(job)
//                }
//
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                //TODO Idk do something if it fails
//            }
//        }
//        entriesList.addValueEventListener(listener)
//    }

    fun readSingleValueFromDbTableWithId(tableName: String, id: String): String {
        val entry = dbRef.child(tableName).child(id)
        var returnObject = ""

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                returnObject = dataSnapshot.value as String
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        entry.addValueEventListener(listener)

        return returnObject
    }
    
    fun <T> readSingleObjectFromDbTableWithId(tableName: String, idName: String, id: String, returnObject: T): T {
        val entry = dbRef.child(tableName).orderByChild(idName).equalTo(id)

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val obj = dataSnapshot.value as HashMap<*, *>
                loadObject(obj, returnObject)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        entry.addValueEventListener(listener)

        return returnObject
    }

    private fun <T> loadObject(obj: HashMap<*, *>, returnObject: T): T {
        when(returnObject) {
            is JobModel -> {
                loadJobModel(obj, returnObject)
            }
            is LocationModel -> {
                loadLocationModel(obj, returnObject)
            }
        }
        return returnObject
    }

    private fun loadJobModel(jobObj: HashMap<*, *>, jobModel: JobModel) {
        with(jobModel) {
            locationId = jobObj["locationId"].toString()
            jobId = jobObj["jobId"].toString()
            jobName = jobObj["jobName"].toString()
            jobState = jobObj["jobState"].toString()
            schedule = jobObj["locationId"].toString()
            payPerHour = jobObj["payPerHour"] as Long
            certificationsRequired = jobObj["certificationsRequired"].toString()
            totalPositionsRequired = jobObj["totalPositionsRequired"] as Long
            totalPositionsFilled = jobObj["totalPositionsFilled"] as Long
        }
    }

    private fun loadLocationModel(locationObj: HashMap<*, *>, locationModel: LocationModel) {
        val addressModel = AddressModel()
        with(locationModel) {
            locationId = locationObj["locationId"].toString()
            businessId = locationObj["businessId"].toString()
            locationName = locationObj["locationName"].toString()
            val addressObj = locationObj["address"] as HashMap<*, *>
            with(addressModel) {
                address = addressObj["address"].toString()
                city = addressObj["city"].toString()
                province = addressObj["province"].toString()
                country = addressObj["country"].toString()
                postalCode = addressObj["postalCode"].toString()
            }
            isVerified = locationObj["verified"] as Boolean
            imageResId = locationObj["imageResId"] as Long
            imgUrl= locationObj["imageUrl"].toString()
        }
    }
}