package com.example.workdash.services

import android.util.Log
import com.example.workdash.Constants
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.matchedJobModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object CheckInService {


    private val dbInt= FirebaseDatabase.getInstance()
    private var matchedJobList = mutableListOf<matchedJobModel>()
    private var finalJobList = mutableListOf<JobModel>()
    fun createMatchedJobModel(jobApplication: JobApplicationModel){
        var newmatchedJobModel = matchedJobModel()
        newmatchedJobModel.jobId = jobApplication.jobId
        newmatchedJobModel.employeeId = jobApplication.employeeId
        DatabaseService.writeToDbTable(Constants.TableNames.MATCHED_JOB_NAME, jobApplication.jobId, newmatchedJobModel)

    }



    fun getJobDetailsByEmployeeId(employeeId: String){
        getMatchedJobsByEmployeeId(employeeId,
            onSuccess = { matchedJobs ->
                // Process the matched job data
                for (job in matchedJobs) {
                    // Do something with each matched job
                    matchedJobList.add(job)
                    getJobModelByJobId("yourJobId",
                        onSuccess = { jobModel ->
                            // Process the job model
                            if (jobModel != null) {
                                // Job model is not null, use it
                                finalJobList.add(jobModel)
                            } else {
                                // Job model is null or not found
                            }
                        },
                        onError = { errorMessage ->
                            // Handle the error
                            Log.e("GET JOB TABLE ERROR", errorMessage)
                        }
                    )

                }
            },
            onError = { errorMessage ->
                // Handle the error
                Log.e("GET MATCHED JOB ERROR", errorMessage)
            }
        )




    }

    fun getJobModelByJobId(jobId: String, onSuccess: (JobModel?) -> Unit, onError: (String) -> Unit) {
        val reference = dbInt.getReference("jobs").child(jobId)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val jobModel = dataSnapshot.getValue(JobModel::class.java)
                onSuccess(jobModel)
            }

            override fun onCancelled(error: DatabaseError) {
                onError("Failed to retrieve job model: ${error.message}")
            }
        })
    }
    private fun getMatchedJobsByEmployeeId(employeeId: String, onSuccess: (List<matchedJobModel>) -> Unit, onError: (String) -> Unit) {
        val reference = dbInt.getReference("matchedJob")

        val query = reference.orderByChild("employeeId").equalTo(employeeId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val matchedJobs = mutableListOf<matchedJobModel>()
                for (snapshot in dataSnapshot.children) {
                    val job = snapshot.getValue(matchedJobModel::class.java)
                    job?.let { matchedJobs.add(it) }
                }
                onSuccess(matchedJobs)
            }

            override fun onCancelled(error: DatabaseError) {
                onError("Failed to retrieve matched job data: ${error.message}")
            }
        })
    }



}