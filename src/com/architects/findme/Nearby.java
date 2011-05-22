package com.architects.findme;

import com.architects.findme.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Nearby extends Activity {
    
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 100; // in Milliseconds
    
	private static final String TAG = "test";
    protected LocationManager locationManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
    }
        
    public void updateLocationHandler(View button) 
    {
    	showCurrentLocation();
    } 

    protected void showCurrentLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(Nearby.this, message,
                    Toast.LENGTH_LONG).show();
        }
    }   

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(Nearby.this, message, Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(Nearby.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(Nearby.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(Nearby.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }
    
	
	// VIEWS
    public void drawNearbyHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Nearby.class);
        startActivity(myIntent);
    }
    public void drawFriendsHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Friends.class);
        startActivity(myIntent);
    }
    public void drawChatHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Chat.class);
        startActivity(myIntent);
    }
    public void drawSearchHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Search.class);
        startActivity(myIntent);
    }
}
