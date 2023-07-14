package com.example.workdash.services

import android.util.Log
import com.example.workdash.Constants
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.matchedJobModel
import com.google.firebase.database.FirebaseDatabase

object CheckInService {


    private val dbRef = FirebaseDatabase.getInstance().reference

    fun createMatchedJobModel(jobApplication: JobApplicationModel){
        var newmatchedJobModel = matchedJobModel()
        newmatchedJobModel.jobId = jobApplication.jobId
        newmatchedJobModel.employeeId = jobApplication.employeeId
        DatabaseService.writeToDbTable(Constants.TableNames.MATCHED_JOB_NAME, jobApplication.jobId, newmatchedJobModel)

    }

    fun getMatchedJobByEmployeeId(employeeId: String){
        val matchedJobList = dbRef.child(Constants.TableNames.MATCHED_JOB_NAME).equalTo(Constants.MatchedJob.EMPLOYEE_ID, employeeId)
        Log.i("MATCHED JOB LIST !!!!!!!!!!!!!!!!!!!", matchedJobList.toString())
    }

    fun getMatchedJobByJobId(jobId: String){
        dbRef.child(Constants.TableNames.MATCHED_JOB_NAME).equalTo(Constants.MatchedJob.JOB_ID, jobId)
    }


}