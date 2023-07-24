package com.example.workdash.viewModels

import androidx.lifecycle.ViewModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.WorkerProfileModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserViewModel: ViewModel() {
    val users = mutableListOf<WorkerProfileModel>()

    init {
        val dbRef = FirebaseDatabase.getInstance().reference
        initJobPostListener(dbRef)
    }

    private fun initJobPostListener(dbRef: DatabaseReference) {
        val userList = dbRef.child("userProfile")

        val userPostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    //TODO filter jobs based on filters
                    val user = WorkerProfileModel()
                    val userObj = userSnapshot.value as HashMap<*, *>
                    with(user) {
                        uid = userObj["uid"].toString()
                        isWorker = userObj["worker"] as Boolean
                        salary = userObj["salary"].toString().toInt()
                        name = userObj["name"].toString()
                        email = userObj["email"].toString()
                        phone = userObj["phone"].toString()
                        address = userObj["address"].toString()
                        isVerified = userObj["verified"] as Boolean
                        workDays = userObj["workDays"] as List<String>
                        startTime = userObj["startTime"].toString()
                        endTime = userObj["endTime"].toString()
                        selectedId = userObj["selectedId"].toString()
                    }
                    users.add(user)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        userList.addValueEventListener(userPostListener)
    }

    fun getUserList(): List<WorkerProfileModel>{
        return users
    }

    fun getUser(userId: String): WorkerProfileModel {
        println(users.size)
        users.forEach { user ->
            if (user.uid == userId) {
                return user
            }
        }
        return WorkerProfileModel()
    }
}