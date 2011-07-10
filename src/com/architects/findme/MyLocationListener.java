package com.architects.findme;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.location.LocationListener;

class MyLocationListener implements LocationListener {

	private static final String TAG = "LocationHelper";
	
    public void onLocationChanged(Location location) {
       /* String message = String.format(
                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );
        Log.v(TAG, message);*/
        //Toast.makeText(myContext, message, Toast.LENGTH_LONG).show();
    }

    public void onStatusChanged(String s, int i, Bundle b) {
        // Toast.makeText(myContext, "Provider status changed", Toast.LENGTH_LONG).show();
    }

    public void onProviderDisabled(String s) {
        /*Toast.makeText(myContext,
                "Provider disabled by the user. GPS turned off",
                Toast.LENGTH_LONG).show();*/
    }

    public void onProviderEnabled(String s) {
       /* Toast.makeText(myContext,
                "Provider enabled by the user. GPS turned on",
                Toast.LENGTH_LONG).show();*/
    }
}