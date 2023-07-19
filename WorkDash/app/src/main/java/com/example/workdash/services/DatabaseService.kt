package com.example.workdash.services

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.workdash.models.AddressModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object DatabaseService {
    private val dbRef = FirebaseDatabase.getInstance().reference

    fun <T : Any> readListOfObjectsFromDbTable(tableName: String, returnObject: T): Flow<List<T>> {
    return callbackFlow {
        val entry = dbRef.child(tableName)
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.e("L", "Snapshot doesn't exist lol")
                    return
                }
                val list = mutableStateListOf<T>()
                for (snapshot in dataSnapshot.children) {
                    val obj = dataSnapshot.children.first().getValue(returnObject::class.java)
                    if (obj != null) {
                        list.add(obj)
                    }
                }
                trySend(list).isSuccess
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        entry.addListenerForSingleValueEvent(listener)
        awaitClose {
            Log.d("closing list ig", "Cancelling listener")
            entry.removeEventListener(listener)
        }
    }
}

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

    fun <T> readSingleValueFromDbTableWithId(tableName: String, id: String, type: T, callback: (obj: T?) -> Unit) {
        val entry = dbRef.child(tableName).child(id)

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val obj = dataSnapshot.child(id).getValue(type!!::class.java)
                    callback.invoke(obj)
                } else {
                    callback.invoke(null)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        entry.addListenerForSingleValueEvent(listener)
    }

    fun readIdValueFromIdTable(tableName: String, id: String, callback: (obj: Long) -> Unit) {
        val entry = dbRef.child(tableName)

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val obj = dataSnapshot.child(id).getValue(Long::class.java)
                    if (obj != null) {
                        callback.invoke(obj)
                    } else {
                        callback.invoke(0)
                    }
                } else {
                    callback.invoke(0)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        entry.addListenerForSingleValueEvent(listener)
    }
    
    fun <T : Any> readSingleObjectFromDbTableWithId(tableName: String, idName: String, id: String, returnObject: T, callback: (obj: T?) -> Unit) {
        val entry = dbRef.child(tableName).orderByChild(idName).equalTo(id)

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val obj = dataSnapshot.children.first().getValue(returnObject::class.java)
                    callback.invoke(obj)
                } else {
                    callback.invoke(null)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        entry.addListenerForSingleValueEvent(listener)
    }
    fun <T : Any> readListOfObjectsFromDbTable(tableName: String, returnObject: T, callback: (obj: SnapshotStateList<T>?) -> Unit) {
        val entry = dbRef.child(tableName)

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    callback.invoke(null)
                    return;
                }
                val list = mutableStateListOf<T>()
                for (snapshot in dataSnapshot.children) {
                    val obj = dataSnapshot.children.first().getValue(returnObject::class.java)
                    if (obj != null) {
                        list.add(obj)
                    }
                }
                Log.d("Please help me...", "database......")
                callback.invoke(list)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        entry.addListenerForSingleValueEvent(listener)
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