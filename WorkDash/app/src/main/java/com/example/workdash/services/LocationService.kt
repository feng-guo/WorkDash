package com.example.workdash.services

import com.example.workdash.Constants
import com.example.workdash.Constants.IdNames.LOCATION_ID_NAME
import com.example.workdash.Constants.TableNames.LOCATION_TABLE_NAME
import com.example.workdash.models.AddressModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import kotlin.math.ceil

object LocationService {

    fun addLocation(locationName: String, address: AddressModel, imgUrl: String) {
        val locationId = IdGeneratorService.generateLocationId()
        //TODO should be businessId here
        val businessId = UserService.getCurrentUserId()
        //TODO what is imageResId
        var locationModel = LocationModel(locationId, businessId, locationName, address, false, 1, imgUrl)
        saveLocation(locationModel)
    }

    private fun saveLocation(locationModel: LocationModel){
        DatabaseService.writeToDbTable(LOCATION_TABLE_NAME, locationModel.locationId, locationModel)
    }

    fun verifyLocation(locationModel: LocationModel) {
        locationModel.isVerified = true
        saveLocation(locationModel)
    }

    fun getLocationFromId(locationId: String): LocationModel {
        val locationModel = LocationModel()
        return DatabaseService.readSingleObjectFromDbTableWithId(LOCATION_TABLE_NAME, LOCATION_ID_NAME, locationId, locationModel)
    }
}