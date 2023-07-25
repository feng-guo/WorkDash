package com.example.workdash.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.matchedJobModel
import com.example.workdash.services.CheckInService
import com.example.workdash.services.CheckInService.getJobDetailsByEmployeeId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class JobViewModel: ViewModel() {
    private val locationViewModel = LocationViewModel()
    //val isFirstJob: Boolean = false //might delete later
    val jobs = mutableListOf<JobModel>()
    val jobApplications = mutableListOf<JobApplicationModel>()
    var matchedJobs = mutableListOf<JobModel>()
    //var matchedJobList = mutableListOf<matchedJobModel>()

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    init {
        val dbRef = FirebaseDatabase.getInstance().reference
        initJobPostListener(dbRef)
        initJobApplicationListener(dbRef)
        initMatchedJobPostListener()
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

    private fun initMatchedJobPostListener() {

        matchedJobs = CheckInService.getJobDetailsByEmployeeId(currentUserUid)

//        CheckInService.getMatchedJobsByEmployeeId(currentUserUid,
//            onSuccess = { matchedJobs ->
//                // Process the matched job data
//                for (job in matchedJobs) {
//                    // Do something with each matched job
//                    if (!matchedJobList.contains(job) && job.checkOutState == "false") {
//                        matchedJobList.add(job)
//                    }
//
//
//                }
//            },
//            onError = { errorMessage ->
//                // Handle the error
//                Log.e("GET MATCHED JOB ERROR", errorMessage)
//            }
//        )
    }

    fun getJobList(): List<JobModel>{
        return jobs
    }
    fun deleteJobList(){

    }

    fun getJobApplicationList(jobId: String): List<JobApplicationModel> {
        var res = mutableListOf<JobApplicationModel>()

        for(job in jobApplications){
            if(job.jobId == jobId && job.applicationStatus == "Pending" && !res.contains(job)){
                res.add(job)
            }
        }

        return res
    }

    fun getJobApplicationListForJob(jobId: String): List<JobApplicationModel> {
        val result = mutableListOf<JobApplicationModel>()
        jobApplications.forEach{ jobApplicationModel ->
            if (jobApplicationModel.jobId == jobId){
                result.add(jobApplicationModel)
            }
        }
        return result
    }

    fun getMatchedJobList(): MutableList<JobModel>{
        var res = mutableListOf<JobModel>()

        for(job in matchedJobs){
            if(job.jobState != "Finished" && !res.contains(job)){
                res.add(job)
            }
        }

        return res
    }

    fun getJobListWithFilter(userId: String, userViewModel: UserViewModel): MutableList<JobModel> {
        val user = userViewModel.getUser(userId)
        val result = mutableListOf<JobModel>()
        jobs.forEach{ jobModel ->
            if (jobModel.payPerHour >= user.salary) {
                result.add(jobModel)
            }
        }
        return result
    }

    fun getJobListForEmployer(userId: String, locationViewModel: LocationViewModel): MutableList<JobModel> {
        val result = mutableListOf<JobModel>()
        jobs.forEach{ jobModel ->
            if (locationViewModel.getLocation(jobModel.locationId).businessId == userId) {
                result.add(jobModel)
            }
        }
        return result
    }

    fun getJob(id: String): JobModel {
        jobs.forEach{ jobModel ->
            if (jobModel.jobId == id) {
                return jobModel
            }
        }
        return JobModel()
    }
}

