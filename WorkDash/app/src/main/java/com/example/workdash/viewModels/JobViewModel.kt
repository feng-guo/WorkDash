package com.example.workdash.viewModels

import androidx.lifecycle.ViewModel
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.JobModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JobViewModel: ViewModel() {
    private val locationViewModel = LocationViewModel()
    //val isFirstJob: Boolean = false //might delete later
    val jobs = mutableListOf<JobModel>()
    val jobApplications = mutableListOf<JobApplicationModel>()

    init {
        val dbRef = FirebaseDatabase.getInstance().reference
        initJobPostListener(dbRef)
        initJobApplicationListener(dbRef)
    }

    private fun initJobPostListener(dbRef: DatabaseReference) {
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

    private fun initJobApplicationListener(dbRef: DatabaseReference) {
        val jobApplicationList = dbRef.child("JobApplications")

        val jobApplicationListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (jobApplicationSnapshot in dataSnapshot.children) {
                    //TODO filter job applications based on job id
                    val jobApplication = JobApplicationModel()
                    val jobApplicationObj = jobApplicationSnapshot.value as HashMap<*, *>
                    with(jobApplication) {
                        jobApplicationId = jobApplicationObj["jobApplicationId"].toString()
                        jobId = jobApplicationObj["jobId"].toString()
                        employeeId = jobApplicationObj["employeeId"].toString()
                        applicationStatus = jobApplicationObj["applicationStatus"].toString()
                    }
                    jobApplications.add(jobApplication)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        jobApplicationList.addValueEventListener(jobApplicationListener)
    }
    fun addJob(job: JobModel){
        FirebaseDatabase.getInstance().reference.child("Jobs").child(job.jobId).setValue(job)
    }
    fun getJobList(): List<JobModel>{
        return jobs
    }
    fun deleteJobList(){

    }

    fun getJobApplicationList(): List<JobApplicationModel> {
        return jobApplications
    }
    fun applyToJob(jobApplication: JobApplicationModel) {
        FirebaseDatabase.getInstance().reference.child("Jobs").child(jobApplication.jobId).setValue(jobApplication)
    }

    fun acceptApplication(jobApplication: JobApplicationModel) {
        //TODO
    }

    fun rejectApplication(jobApplication: JobApplicationModel) {
        //TODO
    }
}

