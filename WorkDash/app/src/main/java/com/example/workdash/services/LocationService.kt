package com.example.workdash.services

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import com.example.workdash.Constants.IdNames.LOCATION_ID_NAME
import com.example.workdash.Constants.TableNames.API_KEY_NAME
import com.example.workdash.Constants.TableNames.COORDINATE_TABLE_NAME
import com.example.workdash.Constants.TableNames.LOCATION_TABLE_NAME
import com.example.workdash.models.AddressModel
import com.example.workdash.models.CoordinateModel
import com.example.workdash.models.LocationModel
import com.google.firebase.annotations.concurrent.Background
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


object LocationService {

    fun addLocation(locationName: String, address: AddressModel, imgUrl: String) {
        var locationId: String
        val businessId = UserService.getCurrentUserId()

        val lmd = { retrievedId : Long ->
            locationId = retrievedId.toString()
            //TODO what is imageResId
            val locationModel = LocationModel(locationId, businessId, locationName, address, false, 1, imgUrl)
//            saveLocation(locationModel)
            verifyLocation(locationModel)
        }
        IdGeneratorService.generateLocationId(lmd)
    }

    private fun saveLocation(locationModel: LocationModel){
        DatabaseService.writeToDbTable(LOCATION_TABLE_NAME, locationModel.locationId, locationModel)
    }

    private fun getGMapsApiKey(callback: (obj: String) -> Unit) {
        DatabaseService.readKeyFromTable(API_KEY_NAME, "maps", callback)
    }
    fun verifyLocation(locationModel: LocationModel) {
        val addressModel = locationModel.address
        val address = addressModel.address + addressModel.city + addressModel.province
        address.replace(" ", "%20")
        val lmd = { key : String ->
            val url = "https://maps.googleapis.com/maps/api/geocode/json?address=$address&key=$key"
            val coordinate = sendRequest(url, locationModel.locationId)
            locationModel.isVerified = true
            saveLocation(locationModel)
            saveCoordinate(coordinate)
        }
        getGMapsApiKey(lmd)

    }

    private fun saveCoordinate(coordinateModel: CoordinateModel){
        DatabaseService.writeToDbTable(COORDINATE_TABLE_NAME, coordinateModel.locationId, coordinateModel)
    }

    fun sendRequest(request: String, locationId: String): CoordinateModel {
        val threadPolicy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(threadPolicy)
        val url = URL(request)
        val connection = url.openConnection()
        BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
            var line: String?
            val sb = StringBuilder()
            while (inp.readLine().also { line = it } != null) {
                sb.append(line)
            }
            val res = JSONObject(sb.toString())
            val array = res.getJSONArray("results")
            val result = array.getJSONObject(0)
            val geometry = result.getJSONObject("geometry")
            val location = geometry.getJSONObject("location")
            val lat = location.getDouble("lat")
            val lng = location.getDouble("lng")
            return CoordinateModel(lat, lng, locationId)
        }
    }

    fun getLocationFromId(locationId: String, callback: (locationModel: LocationModel?) -> Unit) {
        val locationModel = LocationModel()
        DatabaseService.readSingleObjectFromDbTableWithId(LOCATION_TABLE_NAME, LOCATION_ID_NAME, locationId, locationModel, callback)
    }
}