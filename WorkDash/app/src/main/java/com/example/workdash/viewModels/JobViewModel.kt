package com.example.workdash.viewModels

import androidx.lifecycle.ViewModel
import com.example.workdash.models.JobModel

class JobViewModel: ViewModel() {
    private val locationViewModel = LocationViewModel()
    val jobs = listOf(

        JobModel(
            jobID = 123,
            position = "cleaner",
            position_for_employer = "Line Cook",
            employerName_for_employee = "Burger King",
            pay_for_employee = "$17/hour",
            employerName = "Popeyes",
            currentState = "posted",
            schedule = "15:00 - 16:00",
            pay = 18,
            requirements = "N/A",
            totalPositionNumber = 5,
            filledPositionNumber = 4,
            location = locationViewModel.getLocation(0)
        ),
        JobModel(
            jobID = 432,
            position = "cleaner",
            position_for_employer = "Line Cook",
            employerName_for_employee = "Mcdonalds",
            pay_for_employee = "$19/hour",
            employerName = "Popeyes",
            currentState = "posted",
            schedule = "15:00 - 16:00",
            pay = 18,
            requirements = "N/A",
            totalPositionNumber = 5,
            filledPositionNumber = 4,
            location = locationViewModel.getLocation(0)
        ),
        JobModel(
            jobID = 153,
            position = "cleaner",
            position_for_employer = "Line Cook",
            employerName_for_employee = "KFC",
            pay_for_employee = "$18/hour",
            employerName = "Popeyes",
            currentState = "posted",
            schedule = "15:00 - 16:00",
            pay = 18,
            requirements = "N/A",
            totalPositionNumber = 5,
            filledPositionNumber = 4,
            location = locationViewModel.getLocation(0)
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

