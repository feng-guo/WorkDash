package com.example.workdash.models

data class RatingModel(
    var userId: String,
    var ratingsCount: Long,
    var ratingsTotal: Long,
    var ratingAverage: Double
) {
    constructor(): this("", 0, 0, 0.0)
//    constructor(userId: String): this(userId, 0, 0, 0.0)
}
