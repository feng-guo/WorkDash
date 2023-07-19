package com.example.workdash.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workdash.Constants
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.JobModel
import com.example.workdash.services.DatabaseService
import com.example.workdash.services.JobService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class JobViewModel: ViewModel() {
    private val locationViewModel = LocationViewModel()
    //val isFirstJob: Boolean = false //might delete later
//    var jobList = mutableListOf<JobModel>()
    val jobApplications = mutableListOf<JobApplicationModel>()

    private val _jobList = MutableLiveData<List<JobModel>>()
    val jobList: LiveData<List<JobModel>> = _jobList

    init {
        viewModelScope.launch {
            val jobModel = JobModel()
            _jobList.value = DatabaseService.readListOfObjectsFromDbTable(Constants.TableNames.JOB_TABLE_NAME, jobModel)
        }
//        val jobListCallback = { jobs: MutableList<JobModel>? ->
//            Log.d("Help", "HELP ME JOB LIST")
//            jobs?.get(0)?.let { Log.d("GG", it.jobName) }
//            jobList = jobs?:jobList
//        }
//        JobService.getJobList(jobListCallback)


//        val jobListCallback = { jobs: SnapshotStateList<JobModel>? ->
//            jobList = jobs?:jobList
//            var test = Log.d("Please help me...", "currentjobemployers help me callback...")
//            if (jobList.size != 0) {
//                test = Log.d("wo kms", jobList[0].jobName)
//            } else {
//                test = Log.d("wo kms", "WHY")
//            }
//        }
//        JobService.getJobList(jobListCallback)


        val dbRef = FirebaseDatabase.getInstance().reference
//        initJobPostListener(dbRef)
        initJobApplicationListener(dbRef)
    }

    private fun initJobPostListener(dbRef: DatabaseReference) {
        val jobs = dbRef.child("Jobs")

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
                    jobList.add(job)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        jobs.addValueEventListener(jobPostListener)
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

    fun getJobList(): LiveData<List<JobModel>> {


        return jobList
    }
    fun deleteJobList(){

    }

    fun getJobApplicationList(): List<JobApplicationModel> {
        return jobApplications
    }
}

