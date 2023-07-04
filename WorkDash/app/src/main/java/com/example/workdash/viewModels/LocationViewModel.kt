package com.example.workdash.viewModels

import com.example.workdash.models.LocationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LocationViewModel {
    private var locations = mutableListOf<LocationModel>(
//        LocationModel(
//            imageResId = 0,
//            locationName = "Burger King",
//            address = "E7",
//            isVerified = true,
//            imgUrl = "https://perkinswill.com/wp-content/uploads/2019/07/project_Eng5_7_01-2880x1570.jpg",
//            businessId = "1",
//            locationId = "0",
//        ),
//        LocationModel(
//            imageResId = 1,
//            locationName = "McDonald's",
//            address = "85 University Ave E, Waterloo",
//            imgUrl = "https://images.thestarimages.com/O_8rJQwlka4r8Q9ugAlX5v01py4=/1280x1024/smart/filters:cb(1678309224053)/https://www.thestar.com/content/dam/localcommunities/burlington_post/life/food-wine/2023/03/02/finally-here-mcdonald-s-putting-new-sandwich-on-its-menu-in-canada-on-march-7/10860519_mcdonalds.JPG",
//            businessId = "1",
//            isVerified = true,
//            locationId = "1",
//        ),
//        LocationModel(
//            imageResId = 2,
//            locationName = "Lazeez",
//            address = "30 Northfield Dr E, Waterloo",
//            imgUrl = "https://lh3.googleusercontent.com/p/AF1QipNeFZg-WYTJuU8G6yVUc6dgi-NEvSalJhRx8xL8=w1080-h608-p-no-v0",
//            businessId = "1",
//            isVerified = true,
//            locationId = "2",
//        )
    )

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