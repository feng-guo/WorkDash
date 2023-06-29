package com.example.workdash.viewModels

import com.example.workdash.models.CandidateModel

class CandidateViewModel {

    private val candidates = listOf(
        CandidateModel(
            applyID = 0,
            selfDescription = "Job Title 1",
            certification = "Job Location 1",
            rating = 4
        ),
        CandidateModel(
            applyID = 0,
            selfDescription = "Job Title 2",
            certification = "Job Location 2",
            rating = 5
        )
    )

    fun getCandidateList(): List<CandidateModel> {
        return candidates
    }

}