package com.architects.findme;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;
import com.architects.helper.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Nearby extends Activity {
    
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 100; // in Milliseconds
    
	private static final String TAG = "test";
    protected LocationManager locationManager;
    private ListView nearby;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.nearby);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
        
        load();
	}
	
	public void reloadHandler(View v)
	{
		load();
	}	
	
	private void load()
	{ 
        String[] logPref = AccountHelper.getLoginPreferences(this);
        String params = "?mail=" + logPref[0];
        JSONObject json = RequestHelper.doHttpGet("get_online_user.php", this, params);
        String[] onlineUser = new String[json.length()]; 
        
        final String[] mails = new String[json.length()];
        final String[] names = new String[json.length()];
        
        try {
	        Log.v(TAG, "Länge:"+json.length());
	        JSONObject json_data = null;
	        String x;
	        
	        Location location = new Location("");
	        location.setLatitude(13.0443);
	        location.setLongitude(48.2557);
	        
	        for(int i=0;i<json.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = json.getJSONObject(x);
	        	
	        	Location location2 = new Location("");
		        location2.setLatitude(json_data.getDouble("latitude"));
		        location2.setLongitude(json_data.getDouble("longitude"));;
	        	
	        	float distance = location.distanceTo(location2);
	        	String me = null;
	        	int dis = 0;
	        	if (distance >= 1000) 
        		{
        			distance /= 1000;
        			dis = (int) Math.rint(distance);
        			me = dis + " km";
        		}
	        	else me = distance + " m";
	        	
	        	mails[i] = json_data.getString("mail");
	        	names[i] = json_data.getString("name");
	        	
	        	onlineUser[i] = json_data.getString("name") + "\n" + me;
	        }
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nearby = (ListView) findViewById(R.id.nearbylist);
        nearby.setAdapter(new ArrayAdapter<String>(this, R.layout.nearby_list_item, R.id.nearby_list_text, onlineUser));

        nearby.setTextFilterEnabled(true);
        
        nearby.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	    Bundle bundle = new Bundle();
		    	bundle.putString("mail", mails[position]);
		    	bundle.putString("name", names[position]);
	
		    	Intent newIntent = new Intent(getApplicationContext(), Profile.class);
		    	newIntent.putExtras(bundle);
		    	startActivity(newIntent);
          }
        });
    }
        
    public void updateLocationHandler(View button) 
    {
    	updateLocation();
    } 
    public void updateLocation()
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
            // Toast.makeText(Nearby.this, message, Toast.LENGTH_LONG).show();
        }
    }   

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            //Toast.makeText(Nearby.this, message, Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            //Toast.makeText(Nearby.this, "Provider status changed", Toast.LENGTH_LONG).show();
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

    
    public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
