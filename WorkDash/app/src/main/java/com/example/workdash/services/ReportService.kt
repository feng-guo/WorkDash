package com.example.workdash.services

import com.example.workdash.Constants
import com.example.workdash.models.JobApplicationModel
import com.example.workdash.models.LocationModel
import com.example.workdash.models.ReportModel

object ReportService {
    fun createReport(id: String, description: String, isEmployer: Boolean) {
        val fromUserId = UserService.getCurrentUserId()
        if (isEmployer) {
            val lmd = { retrievedJobApplication : JobApplicationModel? ->
                if (retrievedJobApplication != null) {
                    createReport(fromUserId, retrievedJobApplication.employeeId, description)
                }
            }
            JobApplicationService.getJobApplicationFromId(id, lmd)
        } else {
            val lmd = { retrievedLocation : LocationModel? ->
                if (retrievedLocation != null) {
                    createReport(fromUserId, retrievedLocation.businessId, description)
                }
            }
            LocationService.getLocationFromId(id, lmd)
        }
    }

    private fun createReport(fromUserId: String, toUserId: String, description: String) {
        var reportId: String
        val lmd = { retrievedId : Long ->
            reportId = retrievedId.toString()
            val reportModel = ReportModel(reportId, fromUserId, toUserId, description)
            saveReport(reportModel)
        }
        IdGeneratorService.generateReportId(lmd)
    }

    private fun saveReport(report: ReportModel) {
        DatabaseService.writeToDbTable(Constants.TableNames.REPORT_TABLE_NAME, report.reportId, report)
    }
}