package com.architects.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.google.android.maps.GeoPoint;

public class LocationHelper
{
    private static final String TAG = "LocationHelper";
    public static final String PREFS_NAME = "LoginCredentials";
    
    public static Location getCurrentLocation(Context c) 
	{
		Location loc = new Location("");
		
		SharedPreferences preferences = c.getSharedPreferences(PREFS_NAME, 0);
		
		loc.setLongitude(preferences.getFloat("longitude", 0)); 
		loc.setLatitude(preferences.getFloat("latitude", 0)); 
		
		return loc;
	}
    
    
    public static String update(Location location, Context context)
	{
		JSONObject j = new JSONObject();
        try
        {
        	String[] str = AccountHelper.getLoginPreferences(context);
			j.put("longitude", location.getLongitude());
			j.put("latitude", location.getLatitude());
			j.put("mail", str[0]);
			j.put("password", StandardHelper.createHashMd5(str[1]));

			return RequestHelper.doHttpPost("set_location.php", context, j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
    
    
    public static GeoPoint toGeoPoint(String[] coord)
    {
        double lat = Double.parseDouble(coord[0]);
        double lng = Double.parseDouble(coord[1]);
 
        return new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
    }
}
