package com.example.workdash.services

import com.example.workdash.models.AddressModel

object AddressService {
    fun createAddress(address: String, city: String, province: String, country: String, postalCode: String): AddressModel {
        return AddressModel(address, city, province, country, postalCode)
    }

    //TODO make this real
    fun verifyAddress(address: AddressModel): Boolean {
        return true
    }
}