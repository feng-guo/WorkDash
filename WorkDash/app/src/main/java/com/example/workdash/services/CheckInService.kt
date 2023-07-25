package com.example.workdash.services

import android.util.Log
import com.example.workdash.Constants
import com.example.workdash.models.CurrentEmployeeModel
import com.example.workdash.models.EmployerProfileModel
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.matchedJobModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CheckInService {


    private val dbInt= FirebaseDatabase.getInstance()
    private var matchedJobList = mutableListOf<matchedJobModel>()
    private var finalJobList = mutableListOf<JobModel>()
    fun createMatchedJobModel(jobApplication: JobApplicationModel){
        var newmatchedJobModel = matchedJobModel()
        newmatchedJobModel.jobId = jobApplication.jobId
        newmatchedJobModel.employeeId = jobApplication.employeeId
        newmatchedJobModel.jobId_employeeId = jobApplication.jobId + "_" + jobApplication.employeeId
        DatabaseService.writeToDbTable(Constants.TableNames.MATCHED_JOB_NAME, jobApplication.jobId, newmatchedJobModel)

    }



    fun getJobDetailsByEmployeeId(employeeId: String?): MutableList<JobModel>{
        getMatchedJobsByEmployeeId(employeeId,
            onSuccess = { matchedJobs ->
                // Process the matched job data
                for (job in matchedJobs) {
                    // Do something with each matched job
                    if(!matchedJobList.contains(job)){
                        matchedJobList.add(job)
                        getJobModelByJobId(job.jobId,
                            onSuccess = { jobModel ->
                                // Process the job model
                                if (jobModel != null && !finalJobList.contains(jobModel)) {
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


                }
            },
            onError = { errorMessage ->
                // Handle the error
                Log.e("GET MATCHED JOB ERROR", errorMessage)
            }
        )


        return finalJobList

    }

    fun updateJobState(jobId: String, state: String) {
        val reference = dbInt.getReference("Jobs")

        val query = reference.orderByChild("jobId").equalTo(jobId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val Job = snapshot.getValue(JobModel::class.java)
                    Job?.let {
                        // Update the matchedJob model with the new CheckInState and CheckInTime
                        it.jobState = state

                        // Update the matchedJob in Firebase
                        val updates = mutableMapOf<String, Any>()
                        updates[snapshot.key.toString()] = it

                        reference.updateChildren(updates)
                            .addOnSuccessListener {
                                // Update successful
                                // You can perform any action here after the update is successful
                            }
                            .addOnFailureListener { error ->
                                // Handle the error
                                Log.e("UPDATE MATCHED JOB MODEL ERROR", "Failed to update CheckInState and CheckInTime: ${error.message}")
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Log.e("UPDATE MATCHED JOB MODEL ERROR", "Failed to retrieve matched job data: ${error.message}")
            }
        })
    }

    private fun getJobModelByJobId(jobId: String, onSuccess: (JobModel?) -> Unit, onError: (String) -> Unit) {
        val reference = dbInt.getReference("Jobs").child(jobId)

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
    fun getMatchedJobsByEmployeeId(employeeId: String?, onSuccess: (List<matchedJobModel>) -> Unit, onError: (String) -> Unit) {
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


    
    suspend fun getJobDetailsByJobIdAndEmployeeId(jobId: String, employeeId: String): matchedJobModel?{
        return withContext(Dispatchers.IO) {
            var res: matchedJobModel? = null
            findMatchedJobByJobIdAndEmployeeId(jobId, employeeId,
                onSuccess = { matchedJob ->
                    // Process the matched job data
                    if (matchedJob != null) {
                        res = matchedJobModel().apply {
                            this.employeeId = matchedJob.employeeId
                            this.jobId = matchedJob.jobId
                            this.checkInState = matchedJob.checkInState
                            this.checkInTime = matchedJob.checkInTime
                            this.checkOutState = matchedJob.checkOutState
                            this.checkOutTime = matchedJob.checkOutTime
                        }
                    }
                },
                onError = { errorMessage ->
                    // Handle the error
                    Log.e("GET MATCHED JOB ERROR", errorMessage)
                }
            )

            return@withContext res;
        }
    }
    private fun findMatchedJobByJobIdAndEmployeeId(
        jobId: String,
        employeeId: String,
        onSuccess: (matchedJobModel?) -> Unit,
        onError: (String) -> Unit
    ) {
        val reference = dbInt.getReference("matchedJob")

        val query = reference.orderByChild("jobId_employeeId").equalTo(jobId + "_"+ employeeId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var matchedJob: matchedJobModel? = null
                for (snapshot in dataSnapshot.children) {
                    val job = snapshot.getValue(matchedJobModel::class.java)
                    if (job?.jobId == jobId && job.employeeId == employeeId) {
                        matchedJob = job
                        break
                    }
                }
                onSuccess(matchedJob)
            }

            override fun onCancelled(error: DatabaseError) {
                onError("Failed to find matched job: ${error.message}")
            }
        })
    }


    fun updateCheckInStateAndTime(jobId: String, employeeId: String, checkInState: String, checkInTime: String) {
        val reference = dbInt.getReference("matchedJob")

        // Set up the query to find the matchedJob with the provided jobId and employeeId
        val query = reference.orderByChild("jobId_employeeId").equalTo("$jobId"+ "_" + "$employeeId")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val matchedJob = snapshot.getValue(matchedJobModel::class.java)
                    matchedJob?.let {
                        // Update the matchedJob model with the new CheckInState and CheckInTime
                        it.checkInState = checkInState
                        it.checkInTime = checkInTime

                        // Update the matchedJob in Firebase
                        val updates = mutableMapOf<String, Any>()
                        updates[snapshot.key.toString()] = it

                        reference.updateChildren(updates)
                            .addOnSuccessListener {
                                // Update successful
                                // You can perform any action here after the update is successful
                            }
                            .addOnFailureListener { error ->
                                // Handle the error
                                Log.e("UPDATE MATCHED JOB MODEL ERROR", "Failed to update CheckInState and CheckInTime: ${error.message}")
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Log.e("UPDATE MATCHED JOB MODEL ERROR", "Failed to retrieve matched job data: ${error.message}")
            }
        })
    }

    fun updateCheckOutStateAndTime(jobId: String, employeeId: String, checkOutState: String, checkOutTime: String) {
        val reference = dbInt.getReference("matchedJob")

        // Set up the query to find the matchedJob with the provided jobId and employeeId
        val query = reference.orderByChild("jobId_employeeId").equalTo("$jobId"+ "_" + "$employeeId")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val matchedJob = snapshot.getValue(matchedJobModel::class.java)
                    matchedJob?.let {
                        // Update the matchedJob model with the new CheckInState and CheckInTime
                        it.checkOutState = checkOutState
                        it.checkOutTime = checkOutTime

                        // Update the matchedJob in Firebase
                        val updates = mutableMapOf<String, Any>()
                        updates[snapshot.key.toString()] = it

                        reference.updateChildren(updates)
                            .addOnSuccessListener {
                                // Update successful
                                // You can perform any action here after the update is successful
                            }
                            .addOnFailureListener { error ->
                                // Handle the error
                                Log.e("UPDATE MATCHED JOB MODEL ERROR", "Failed to update CheckInState and CheckInTime: ${error.message}")
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Log.e("UPDATE MATCHED JOB MODEL ERROR", "Failed to retrieve matched job data: ${error.message}")
            }
        })
    }

    fun getMatchedJobFromJobIdAndEmployeeId(jobId_employeeId: String, callback: (matchedJobModel: matchedJobModel?) -> Unit) {
        val matchedJobModel = matchedJobModel()
        DatabaseService.readSingleObjectFromDbTableWithId(
            Constants.TableNames.MATCHED_JOB_NAME,
            Constants.MatchedJob.JOB_EMPLOYEE, jobId_employeeId, matchedJobModel, callback)
    }

    private fun findUserByEmployeeId(
        employeeId: String,
        onSuccess: (EmployerProfileModel?) -> Unit,
        onError: (String) -> Unit
    ) {
        val reference = dbInt.getReference("userProfile")

        val query = reference.orderByChild("uid").equalTo(employeeId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user: EmployerProfileModel? = null
                for (snapshot in dataSnapshot.children) {
                    val getuser = snapshot.getValue(EmployerProfileModel::class.java)
                    if (getuser?.uid == employeeId) {
                        user = getuser
                        break
                    }
                }
                onSuccess(user)
            }

            override fun onCancelled(error: DatabaseError) {
                onError("Failed to find matched job: ${error.message}")
            }
        })
    }

    fun getUserByEmployeeId(employeeId: String, callback: (employerProfile: EmployerProfileModel?) -> Unit) {
        val employerProfileModel = EmployerProfileModel()
        DatabaseService.readSingleObjectFromDbTableWithId(
            Constants.TableNames.USER_PROFILE,
            Constants.UserProfile.UID, employeeId, employerProfileModel, callback)
    }


    private fun getmatchedJobByJobId(jobId: String?, onSuccess: (List<matchedJobModel>) -> Unit, onError: (String) -> Unit) {
        val reference = dbInt.getReference("matchedJob")
        val query = reference.orderByChild("jobId").equalTo(jobId)
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

    fun getmatchedJobDetailsByJobId(jobId: String?, currentEmployeeCallback: (List<CurrentEmployeeModel>) -> Unit) {
        val matchedJobList = mutableListOf<matchedJobModel>()
        val currentEmployeeList = mutableListOf<CurrentEmployeeModel>()

        getmatchedJobByJobId(
            jobId,
            onSuccess = { matchedJobs ->
                // Process the matched job data
                for (job in matchedJobs) {
                    // Do something with each matched job
                    if (!matchedJobList.contains(job)) {
                        matchedJobList.add(job)
                        getUserByEmployeeId(
                            job.employeeId,
                            callback = { employeeModel ->
                                // Process the job model
                                if (employeeModel != null) {
                                    // Job model is not null, use it
                                    // Check if all jobs have been processed
                                    var tempCurrentEmployeeModel = CurrentEmployeeModel()
                                    tempCurrentEmployeeModel.PhoneNum = employeeModel.phone
                                    tempCurrentEmployeeModel.jobId = job.jobId
                                    tempCurrentEmployeeModel.employeeName = employeeModel.name
                                    tempCurrentEmployeeModel.checkOutState = job.checkOutState
                                    tempCurrentEmployeeModel.checkOutTime = job.checkOutTime
                                    tempCurrentEmployeeModel.checkInState = job.checkInState
                                    tempCurrentEmployeeModel.checkInTime = job.checkInTime
                                    tempCurrentEmployeeModel.uid = employeeModel.uid
                                    currentEmployeeList.add(tempCurrentEmployeeModel)
                                    currentEmployeeCallback(currentEmployeeList)
                                } else {
                                    // Job model is null or not found
                                    println("employee not found ")
                                }
                            }

                        )

                    }
                }
                // Check if there are no matched jobs
                if (matchedJobs.isEmpty()) {
                    // No matched jobs, invoke the callback with an empty list
                    currentEmployeeCallback(emptyList())
                }
            },
            onError = { errorMessage ->
                // Handle the error
                Log.e("GET MATCHED JOB ERROR", errorMessage)
            }
        )

    }

}