package com.example.workdash.viewModels

import com.example.workdash.models.AddressModel
import com.example.workdash.models.CoordinateModel
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow

class LocationViewModel {
    private var locations = mutableListOf<LocationModel>()

    private var coordinates = mutableListOf<CoordinateModel>()

    init {
        val dbRef = FirebaseDatabase.getInstance().reference
        val locationList = dbRef.child("Locations")

        val locationListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (locationSnapshot in dataSnapshot.children) {
                    val location = LocationModel()
                    val locationObj = locationSnapshot.value as HashMap<*, *>
                    with(location) {
                        locationId = locationObj["locationId"].toString()
                        businessId = locationObj["businessId"].toString()
                        locationName = locationObj["locationName"].toString()
                        val addressModel = AddressModel()
                        val addressObj = locationObj["address"]  as HashMap<*, *>
                        with(addressModel) {
                            address = addressObj["address"].toString()
                            city = addressObj["city"].toString()
                            province = addressObj["province"].toString()
                            country = addressObj["country"].toString()
                            postalCode = addressObj["postalCode"].toString()
                        }
                        address = addressModel
                        isVerified = locationObj["verified"] as Boolean
                        imageResId = locationObj["imageResId"] as Long
                        imgUrl= locationObj["imgUrl"].toString()
                    }
                    locations.add(location)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        locationList.addValueEventListener(locationListListener)

        val coordinateList = dbRef.child("Coordinates")
        val coordinateListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (coordinateSnapshot in dataSnapshot.children) {
                    val coordinate = CoordinateModel()
                    val coordinateObj = coordinateSnapshot.value as HashMap<*, *>
                    with(coordinate) {
                        locationId = coordinateObj["locationId"].toString()
                        longitude = coordinateObj["longitude"] as Double
                        latitude = coordinateObj["latitude"] as Double
                    }
                    coordinates.add(coordinate)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        coordinateList.addValueEventListener(coordinateListListener)

    }

    fun getLocationList(): List<LocationModel>{
        return locations
    }

    fun getCoordinateList(): List<CoordinateModel> {
        return coordinates
    }

    fun getLocation(id:String): LocationModel {
        for(location in locations) {
            if (location.locationId == id) {
                return location
            }
        }
        return LocationModel()
    }

    fun getJobFromLocation(id: String): JobModel {
        for (job in JobViewModel().jobs) {
            if (job.locationId == id) {
                return job
            }
        }
        return JobModel()
    }

    fun getLocationListEmployer(userId: String): List<LocationModel> {
        val result = mutableListOf<LocationModel>()
        locations.forEach{ locationModel ->
            if (locationModel.businessId == userId) {
                result.add(locationModel)
            }
        }
        return result
    }

    fun getCoordinate(id: String): CoordinateModel {
        for(coordinate in coordinates) {
            if (coordinate.locationId == id) {
                return coordinate
            }
        }
        return CoordinateModel()
    }
}