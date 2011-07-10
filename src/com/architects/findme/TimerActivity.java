package com.architects.findme;

import com.architects.helper.*;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class TimerActivity extends Activity {

	private static final String TAG = "LocationTimer";
	public static final String PREFS_NAME = "LoginCredentials";
	private Handler handler = new Handler();
	private LocationHelper locHelper;
	private Location location;
    protected LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 300000; // in Milliseconds
    private MyLocationListener locListener = new MyLocationListener();
	
	
	@Override
	public void onPause()
	{
		Log.v(TAG, "PAUSE");
		handler.removeCallbacks(locationInterval);
		super.onPause();
	}
	
	@Override
	public void onResume()
	{
		Log.v(TAG, "RESUME");
		super.onResume();
		run();
	}
	

	private Runnable locationInterval = new Runnable() {

		public void run() {
			Log.v(TAG, "... timer");
			
			TimerActivity.this.updateLocation();
			TimerActivity.this.proofLogin();
			
            // recursion
			handler.removeCallbacks(locationInterval);
			handler.postDelayed(this, MINIMUM_TIME_BETWEEN_UPDATES);
		}
	};
	
	public void run() {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(locListener);
		
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                locListener
        );
        
		locationInterval.run();
	}
	
	
	protected void updateLocation() {
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		SharedPreferences preferences = TimerActivity.this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        
        Log.v(TAG, "LONG: " + location.getLongitude() + ", LAT: " + location.getLatitude());
        
        prefsEditor.putFloat("longitude", (float) location.getLongitude());
        prefsEditor.putFloat("latitude", (float) location.getLatitude());
        prefsEditor.commit();
        
        LocationHelper.update(location, TimerActivity.this);
	}
	
	protected void proofLogin() {
		if(!AccountHelper.isLogged(this))
		{
			Log.v(TAG, "NOT LOGGED IN! logging out ...");
			AccountHelper.logout(this);

			handler.removeCallbacks(locationInterval);
			
			finish();
            Intent myIntent = new Intent(this.getApplicationContext(), Main.class);
        	startActivity(myIntent);
		}
	}
}
