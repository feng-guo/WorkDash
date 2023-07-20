package com.example.workdash.models

data class CoordinateModel(
    var latitude: Double,
    var longitude: Double,
    var locationId: String
) {
    constructor() : this(0.0, 0.0, "")
}
