package com.example.workdash.viewModels

import androidx.lifecycle.ViewModel
import com.example.workdash.models.JobModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JobViewModel: ViewModel() {
    private val locationViewModel = LocationViewModel()
    //val isFirstJob: Boolean = false //might delete later
    val jobs = mutableListOf<JobModel>()

    init {
        val dbRef = FirebaseDatabase.getInstance().reference
        val jobList = dbRef.child("Jobs")

        val jobPostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobSnapshot in dataSnapshot.children) {
                    //TODO filter jobs based on filters
                    val job = JobModel()
                    val jobObj = jobSnapshot.value as HashMap<*, *>
                    with(job) {
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
                    jobs.add(job)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        jobList.addValueEventListener(jobPostListener)
    }
    fun addJob(job: JobModel){
        FirebaseDatabase.getInstance().reference.child("Jobs").child(job.jobId).setValue(job)
    }
    fun getJobList(): List<JobModel>{
        return jobs
    }
    fun deleteJobList(){

    }
}

