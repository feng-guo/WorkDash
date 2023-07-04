package com.example.workdash.models

data class LocationModel(
    var locationId: String,
    var businessId: String,

    //TODO implement address model later
//    val address: AddressModel,

    var locationName: String,
    var address: String,
    var isVerified: Boolean,

    //TODO we might have to change this later
    var imageResId: Long,
    var imgUrl: String
) {
    constructor() : this("", "", "", "", false, 0, "") {}
}
