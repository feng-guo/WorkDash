package com.example.workdash.viewModels

import androidx.lifecycle.ViewModel
import com.example.workdash.models.JobModel

class JobViewModel: ViewModel() {
    private val locationViewModel = LocationViewModel()
    val jobs = listOf(
        JobModel(
            jobID = 123,
            position = "cleaner",
            employerName = "Popeyes",
            currentState = "posted",
            schedule = "15:00 - 16:00",
            pay = 18,
            requirements = "N/A",
            totalPositionNumber = 5,
            filledPositionNumber = 4,
            location = locationViewModel.getLocation()
        ),
        JobModel(
            jobID = 123,
            position = "cleaner",
            employerName = "Popeyes",
            currentState = "posted",
            schedule = "15:00 - 16:00",
            pay = 18,
            requirements = "N/A",
            totalPositionNumber = 5,
            filledPositionNumber = 4,
            location = locationViewModel.getLocation()
        ),
        JobModel(
            jobID = 123,
            position = "cleaner",
            employerName = "Popeyes",
            currentState = "posted",
            schedule = "15:00 - 16:00",
            pay = 18,
            requirements = "N/A",
            totalPositionNumber = 5,
            filledPositionNumber = 4,
            location = locationViewModel.getLocation()
        )
    )

    fun addJob(){

    }
    fun getJobList(): List<JobModel>{
        return jobs
    }
    fun deleteJobList(){

    }
}

