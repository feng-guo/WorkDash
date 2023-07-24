package com.example.workdash.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workdash.models.matchedJobModel
import com.example.workdash.services.CheckInService
import com.example.workdash.services.CheckInService.getJobDetailsByJobIdAndEmployeeId
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckInSysWorkerViewModel : ViewModel(){

    var checkInButtonColor by mutableStateOf(Color.Green)
    var checkOutButtonColor by mutableStateOf(Color.Gray)


    fun checkIn(jobId: String, currentUserId: String){
        val currentTimeMillis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        CheckInService.updateCheckInStateAndTime(jobId,currentUserId, "True", dateFormat.format(Date(currentTimeMillis)))
        checkInButtonColor = Color.Gray
        checkOutButtonColor = Color.Green
    }
    fun checkOut(jobId: String, currentUserId: String){
        val currentTimeMillis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        CheckInService.updateCheckOutStateAndTime(jobId,currentUserId, "True", dateFormat.format(Date(currentTimeMillis)))
        checkOutButtonColor = Color.Gray
    }

    fun GetJobDetailsByJobIdAndEmployeeId(jobId:String, employeeId: String) : matchedJobModel?{
        println("GetJobDetailsByJobIdAndEmployeeId been called")
        var res: matchedJobModel? = null
        viewModelScope.launch {
                val jobDetails = CheckInService.getJobDetailsByJobIdAndEmployeeId(jobId, employeeId)
                res = matchedJobModel().apply {
                    if(jobDetails != null){
                        this.employeeId = jobDetails.employeeId
                        this.jobId = jobDetails.jobId
                        this.checkInState = jobDetails.checkInState
                        this.checkInTime = jobDetails.checkInTime
                        this.checkOutState = jobDetails.checkOutState
                        this.checkOutTime = jobDetails.checkOutTime
                    }
                }


            if (jobDetails != null) {
                // Process the matched job details
                println("Job ID: ${jobDetails.jobId}, CheckInState: ${jobDetails.checkInState}")
            } else {
                // No matched job found
                println("No matched job found for the provided IDs.")
            }
        }
        println("final res is : " + res == null + " state : "+ res?.checkInState)

        return res
    }
}