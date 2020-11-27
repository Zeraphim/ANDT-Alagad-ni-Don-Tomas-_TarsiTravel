package com.example.hackfest

import android.graphics.Color
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class LocationDetails {
    var results = ArrayList<Results>() // routes is a JSONArray, therefore you must use an ArrayList to fit data inside.
}

class Results {
    val formatted_address = ""// same as routes
}