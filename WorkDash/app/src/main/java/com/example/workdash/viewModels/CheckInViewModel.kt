package com.example.workdash.viewModels

import android.util.Log
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.matchedJobModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CheckInViewModel {

    private val locationViewModel = LocationViewModel()
    val jobs = mutableListOf<JobModel>()
    val jobApplications = mutableListOf<JobApplicationModel>()
    var currentMatchedJob = matchedJobModel()
    var currentUserUid = ""
    val dbRef = FirebaseDatabase.getInstance().reference


    init {
//        initCheckInWorkerListener(dbRef)
//        initCheckInEmployerListener(dbRef)
    }

    private fun getMatchedJobByWorker(){
        val query = dbRef.child("userProfile").orderByChild("uid").equalTo(currentUserUid)
        Log.i("testing!!!!", query.toString())
    }

    private fun getMatchedJobByEmployer(){

    }
//    private fun initCheckInWorkerListener(dbRef: DatabaseReference){
//        val jobApplicationList = dbRef.child("Jobs").get()
//        println(jobApplicationList.toString())
//    }
//
//    private fun initCheckInEmployerListener(dbRef: DatabaseReference){
//
//    }

    private fun getUid(){
        currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    }

    fun jobCheckIn(jobID: Int){

    }

    fun jobCheckOut(jobID: Int){

    }
}