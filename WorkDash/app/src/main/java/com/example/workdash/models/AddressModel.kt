package com.example.workdash.models

data class AddressModel(
    var address: String,
    var city: String,
    var province: String,
    var country: String,
    var postalCode: String
) {
    constructor() : this("", "", "", "", "")
}