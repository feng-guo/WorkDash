package com.example.workdash.models

data class LocationModel(
    val locationId: String,
    val businessId: String,
    val locationName: String,
//    val address: AddressModel,
    val isVerified: Boolean,



    val imageResId: Int,
    val name: String,
    val address: String,
    val verification: String,
    val imgUrl: String
)
