package com.example.workdash.services

import com.example.workdash.Constants.IdNames.USER_ID_NAME
import com.example.workdash.Constants.TableNames.RATING_TABLE_NAME
import com.example.workdash.models.RatingModel

object RatingService {
    fun updateRating(userId: String, rating: Long) {
        val lmd = { retrievedRating : RatingModel? ->
            if (retrievedRating != null) {
                retrievedRating.ratingsCount++
                retrievedRating.ratingsTotal+= rating
                retrievedRating.ratingAverage = retrievedRating.ratingsTotal.toDouble()/retrievedRating.ratingsCount.toDouble()
                DatabaseService.writeToDbTable(RATING_TABLE_NAME, userId, retrievedRating)
            } else {
                createRating(userId, rating)
            }
        }
        getRatingFromId(userId, lmd)
    }

    private fun createRating(userId: String, rating: Long) {
        val ratingModel = RatingModel(userId, 1, rating, rating.toDouble())
        DatabaseService.writeToDbTable(RATING_TABLE_NAME, userId, ratingModel)
    }

    private fun getRatingFromId(ratingId: String, callback: (ratingModel: RatingModel?) -> Unit) {
        val ratingModel = RatingModel()
        DatabaseService.readSingleObjectFromDbTableWithId(RATING_TABLE_NAME, USER_ID_NAME, ratingId, ratingModel, callback)
    }
}