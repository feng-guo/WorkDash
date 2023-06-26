package com.example.workdash.viewModels

import com.example.workdash.models.CandidateModel

class CandidateViewModel {

    private val candidates = listOf(
        CandidateModel(
            applyID = 0,
            selfDescription = "Job Title 1",
            Certification = "Job Location 1",
            Rating = 4
        ),
        CandidateModel(
            applyID = 0,
            selfDescription = "Job Title 2",
            Certification = "Job Location 2",
            Rating = 5
        )
    )

    fun getCandidateList(): List<CandidateModel> {
        return candidates
    }

}