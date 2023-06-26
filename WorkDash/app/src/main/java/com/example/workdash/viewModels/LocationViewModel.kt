package com.example.workdash.viewModels

import com.example.workdash.models.LocationModel

class LocationViewModel {
    private var locations = mutableListOf<LocationModel>(
        LocationModel(
            imageResId = 0,
            name = "Popeyes",
            address = "85 University Ave E, Waterloo",
            verification = "N/A"
        ),
        LocationModel(
            imageResId = 0,
            name = "Burger King",
            address = "30 Northfield Dr E, Waterloo",
            verification = "N/A"
        )
    )


    fun getLocationList(): List<LocationModel>{
        return locations
    }

    fun getLocation(num:Int): LocationModel{
        return locations.get(num)
    }

    fun addLocation(imageResId: Int, name: String, address: String, verification: String){
        val newLocation =
            LocationModel(
                imageResId = imageResId,
                name = name,
                address = address,
                verification = verification
            )
        locations.add(newLocation)
    }
}