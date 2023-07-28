package com.example.workdash.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workdash.models.MatchedJobModel
import com.example.workdash.services.CheckInService
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
        CheckInService.updateCheckInStateAndTime(jobId,currentUserId, "true", dateFormat.format(Date(currentTimeMillis)))
        checkInButtonColor = Color.Gray
        checkOutButtonColor = Color.Green
    }
    fun checkOut(jobId: String, currentUserId: String){
        val currentTimeMillis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        CheckInService.updateCheckOutStateAndTime(jobId,currentUserId, "true", dateFormat.format(Date(currentTimeMillis)))
        checkOutButtonColor = Color.Gray
        CheckInService.updateJobState(jobId, "Finished")
    }

    fun GetJobDetailsByJobIdAndEmployeeId(jobId:String, employeeId: String) : MatchedJobModel?{
        var res: MatchedJobModel? = null
        viewModelScope.launch {
                val jobDetails = CheckInService.getJobDetailsByJobIdAndEmployeeId(jobId, employeeId)
                res = MatchedJobModel().apply {
                    if(jobDetails != null){
                        this.employeeId = jobDetails.employeeId
                        this.jobId = jobDetails.jobId
                        this.checkInState = jobDetails.checkInState
                        this.checkInTime = jobDetails.checkInTime
                        this.checkOutState = jobDetails.checkOutState
                        this.checkOutTime = jobDetails.checkOutTime
                    }
                }



        }

        return res
    }
}