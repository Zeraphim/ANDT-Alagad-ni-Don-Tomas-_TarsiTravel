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
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

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
                    if (!locationDetermined) {
                        setUserLocation(LatLng(location.latitude, location.longitude))
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        startLocationUpdates()
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

    fun setUserLocation(location : LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location)) // The camera moves to the marker's position.
        mMap.moveCamera(CameraUpdateFactory.zoomTo(20f))
    }
}