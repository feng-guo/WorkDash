package com.example.workdash.models

data class LocationModel(
    var locationId: String,
    var businessId: String,

    var locationName: String,
    var address: AddressModel,
    var isVerified: Boolean,
    var imageResId: Long,
    var imgUrl: String
) {
    constructor() : this("", "", "", AddressModel("", "", "", "", ""),  false, 0, "")
}
