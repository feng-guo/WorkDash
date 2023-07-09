package com.example.workdash.models

data class LocationModel(
    var locationId: String,
    var businessId: String,

    var locationName: String,
    val address: AddressModel,
    var isVerified: Boolean,

    //TODO we might have to change this later
    var imageResId: Long,
    var imgUrl: String
) {
    constructor() : this("", "", "", AddressModel("", "", "", "", ""),  false, 0, "")
}
