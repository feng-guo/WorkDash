package com.example.workdash.viewModels

import com.example.workdash.models.LocationModel

class LocationViewModel {
    private var locations = mutableListOf<LocationModel>(
        LocationModel(
            imageResId = 0,
            name = "Burger King",
            address = "E7",
            verification = "N/A",
            imgUrl = "https://perkinswill.com/wp-content/uploads/2019/07/project_Eng5_7_01-2880x1570.jpg",
            businessId = "1",
            isVerified = true,
            locationId = "0",
            locationName = "here"
        ),
        LocationModel(
            imageResId = 1,
            name = "McDonald's",
            address = "85 University Ave E, Waterloo",
            verification = "N/A",
            imgUrl = "https://images.thestarimages.com/O_8rJQwlka4r8Q9ugAlX5v01py4=/1280x1024/smart/filters:cb(1678309224053)/https://www.thestar.com/content/dam/localcommunities/burlington_post/life/food-wine/2023/03/02/finally-here-mcdonald-s-putting-new-sandwich-on-its-menu-in-canada-on-march-7/10860519_mcdonalds.JPG",
            businessId = "1",
            isVerified = true,
            locationId = "1",
            locationName = "here"
        ),
        LocationModel(
            imageResId = 2,
            name = "Lazeez",
            address = "30 Northfield Dr E, Waterloo",
            verification = "N/A",
            imgUrl = "https://lh3.googleusercontent.com/p/AF1QipNeFZg-WYTJuU8G6yVUc6dgi-NEvSalJhRx8xL8=w1080-h608-p-no-v0",
            businessId = "1",
            isVerified = true,
            locationId = "2",
            locationName = "here"
        )
    )


    fun getLocationList(): List<LocationModel>{
        return locations
    }

    fun getLocation(num:Int): LocationModel{
        return locations.get(num)
    }

    fun addLocation(imageResId: Int, name: String, address: String, verification: String, imgUrl: String, businessId: String, isVerified: Boolean, locationId: String, locationName: String){
        val newLocation =
            LocationModel(
                imageResId = imageResId,
                name = name,
                address = address,
                verification = verification,
                imgUrl = imgUrl,
                businessId = businessId,
                isVerified = isVerified,
                locationId = locationId,
                locationName = locationName
            )
        locations.add(newLocation)
    }
}