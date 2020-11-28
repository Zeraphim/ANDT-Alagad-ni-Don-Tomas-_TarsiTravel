package com.example.hackfest

// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.util.regex.Pattern
import com.google.gson.Gson

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    var locationDetermined = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    Toast.makeText(applicationContext, "${location.latitude} and ${location.longitude}", Toast.LENGTH_LONG).show()
                    setUserLocation(LatLng(location.latitude, location.longitude))
                    stopLocationUpdates()
                }
            }
        }
        val searchView = findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        startLocationUpdates()
        mMap.setOnMapClickListener(this)

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // PRIORITY_HIGH_ACCURACY means that both GPS and local (cell tower) coordinates are used. PRIORITY_LOW_ACCURACY only uses GPS.
        locationRequest.interval = 10000 // An interval of 10s at its slowest.
        locationRequest.fastestInterval = 5000 // An interval of 5s at its fastest.

        val builder = LocationSettingsRequest.Builder() // The request to start location services is started.
        builder.addLocationRequest(locationRequest) // The settings included in mLocationRequest is added to LocationSettingsRequest.
        val locationSettingsRequest = builder.build() // The request is built.
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest) // Checks if Location is turned on.
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun setUserLocation(location : LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location)) // The camera moves to the marker's position.
        mMap.moveCamera(CameraUpdateFactory.zoomTo(20f))
    }

    override fun onMapClick(point: LatLng) {
        getJSON("${point.latitude}, ${point.longitude}")
    }

    fun getJSON(addressToGeocode: String) { // An HTTP GET request is sent to the Google Maps API
        val queue = Volley.newRequestQueue(this) // A new Request queue is initialized. (Useful for multiple requests, but our app doesn't need that)
        val apiKey = getString(R.string.google_maps_key) // The API key, stored as a value in "strings.xml"
        var jsonobj = JSONObject() // Defines the file being received as a JSON.
        val url =
                "https://maps.googleapis.com/maps/api/geocode/json?address=${addressToGeocode}&key=${apiKey}" // The URL to be used.
        val jsonObjectRequest = JsonObjectRequest( // Defines the HTTP GET request as a JSONObjectRequest
                Request.Method.GET, url, jsonobj, // GET is the method of the HTTP request, url is the URL, and jsonobj is the expected data to be received.
                { response -> // A lambda listener that executes code if the request is successful.

                    Toast.makeText(this, "Volley request success", Toast.LENGTH_SHORT)
                            .show() // prints a success message
                    getLocationDetails(response) // the JSONObject is sent to parseDirectionsJSON()
                },
                { error -> // A lambda listener that executes code if the request failed.
                    Toast.makeText(this, "Volley request failed", Toast.LENGTH_SHORT)
                            .show() // prints an error message
                    // TODO: Handle error
                })
        queue.add(jsonObjectRequest) // The request is added to the Queue.
    }

    fun getLocationDetails(response : JSONObject) {// query received JSON for data here
        val locationDetails = Gson().fromJson(response.toString(), LocationDetails::class.java)
        var intent : Intent = Intent(this@MapsActivity, CityDetails::class.java);
        intent.putExtra("ADDRESS", locationDetails.results[0].formatted_address);
        startActivity(intent)
    }
}