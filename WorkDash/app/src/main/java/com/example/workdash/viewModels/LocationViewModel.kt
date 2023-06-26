package com.example.workdash.viewModels

import com.example.workdash.models.LocationModel

class LocationViewModel {
    private val locations = listOf(
        LocationModel(
            imageResId = 0,
            name = "Popeyes",
            address = "85 University Ave E, Waterloo"
        ),
        LocationModel(
            imageResId = 0,
            name = "Burger King",
            address = "30 Northfield Dr E, Waterloo"
        )
    )

    private val location =
        LocationModel(
            imageResId = 0,
            name = "Popeyes",
            address = "85 University Ave E, Waterloo"
        )

    fun getLocationList(): List<LocationModel>{
        return locations
    }

    fun getLocation(): LocationModel{
        return location
    }

}