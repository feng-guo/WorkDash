package com.example.workdash.viewModels

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
                    //TODO filter locations
                    val location = LocationModel()
                    val locationObj = locationSnapshot.value as HashMap<*, *>
                    with(location) {
                        locationId = locationObj["locationId"].toString()
                        businessId = locationObj["businessId"].toString()
                        locationName = locationObj["locationName"].toString()
                        //TODO figure out how to retrieve the object
//                        address = locationObj["address"].toString()
                        isVerified = locationObj["verified"] as Boolean
                        imageResId = locationObj["imageResId"] as Long
                        imgUrl= locationObj["imageUrl"].toString()
                    }
                    locations.add(location)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                //TODO Idk do something if it fails
            }
        }
        locationList.addValueEventListener(locationListListener)

        val coordinateList = dbRef.child("Coordinates")
        val coordinateListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (coordinateSnapshot in dataSnapshot.children) {
                    //TODO filter locations
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
                //TODO Idk do something if it fails
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

    fun getCoordinate(id: String): CoordinateModel {
        for(coordinate in coordinates) {
            if (coordinate.locationId == id) {
                return coordinate
            }
        }
        return CoordinateModel()
    }
}