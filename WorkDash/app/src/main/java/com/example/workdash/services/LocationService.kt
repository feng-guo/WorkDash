package com.example.workdash.services

import com.example.workdash.Constants.IdNames.LOCATION_ID_NAME
import com.example.workdash.Constants.TableNames.LOCATION_TABLE_NAME
import com.example.workdash.models.AddressModel
import com.example.workdash.models.LocationModel

object LocationService {

    fun addLocation(locationName: String, address: AddressModel, imgUrl: String) {
        var locationId: String
//        TODO should be businessId here
        val businessId = UserService.getCurrentUserId()

        val lmd = { retrievedId : String ->
            locationId = retrievedId
            //TODO what is imageResId
            val locationModel = LocationModel(locationId, businessId, locationName, address, false, 1, imgUrl)
            saveLocation(locationModel)
        }
        IdGeneratorService.generateLocationId(lmd)
    }

    private fun saveLocation(locationModel: LocationModel){
        DatabaseService.writeToDbTable(LOCATION_TABLE_NAME, locationModel.locationId, locationModel)
    }

    fun verifyLocation(locationModel: LocationModel) {
        locationModel.isVerified = true
        saveLocation(locationModel)
    }

    fun getLocationFromId(locationId: String, callback: (locationModel: LocationModel?) -> Unit) {
        val locationModel = LocationModel()
        DatabaseService.readSingleObjectFromDbTableWithId(LOCATION_TABLE_NAME, LOCATION_ID_NAME, locationId, locationModel, callback)
    }
}