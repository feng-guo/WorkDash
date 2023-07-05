package com.example.workdash.viewModels

import com.example.workdash.models.LocationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LocationViewModel {
    private var locations = mutableListOf<LocationModel>()

    init {
        var dbRef = FirebaseDatabase.getInstance().reference
        var locationList = dbRef.child("Locations")
//
//        var locationsMap = locationList.chiget()
//        println(locationsMap)
////        for (location in locationsMap ) {
////            locations.add(location.value as LocationModel)
////        }

        val locationListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (locationSnapshot in dataSnapshot.children) {
                    //TODO filter locations
                    var location = LocationModel()
                    var locationObj = locationSnapshot.value as HashMap<*, *>
                    with(location) {
                        locationId = locationObj["locationId"].toString()
                        businessId = locationObj["businessId"].toString()
                        locationName = locationObj["locationName"].toString()
                        address = locationObj["address"].toString()
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
    }

    fun getLocationList(): List<LocationModel>{
        return locations
    }

    fun getLocation(num:Int): LocationModel{
        return locations.get(num)
    }

    fun addLocation(location: LocationModel){
        FirebaseDatabase.getInstance().reference.child("Locations").child(location.locationId).setValue(location)
    }
}