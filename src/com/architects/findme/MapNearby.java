package com.architects.findme;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import com.architects.helper.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
 
public class MapNearby extends MapActivity 
{    
	public int standardZoom = 12;
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private static final String TAG = "test";
    
	private MapController mapController;
	private List<Overlay> mapOverlays;
	private MapView mapView;
	private GeoPoint geoPoint;
	private PeopleItemizedOverlay itemizedoverlay;
	protected LocationManager locationManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_map);
        
        
        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(Html.fromHtml("Map"));
        
        // initialize map view
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setStreetView(true);
        
        // get map mod vars
        mapOverlays = mapView.getOverlays();
        mapController = mapView.getController();
        
        
        
        // go to my location
        String coordinates[] = new String[2];
        coordinates[0] = String.valueOf("30");
        coordinates[1] = String.valueOf("-122");
        geoPoint = LocationHelper.toGeoPoint(coordinates);
        mapController.animateTo(geoPoint);
        mapController.setZoom(standardZoom); 
        mapView.invalidate();
        
        // show my marker
        Drawable marker = MapNearby.this.getResources().getDrawable(R.drawable.marker);
        addMe(mapOverlays);
        
        
        // show people marker
        itemizedoverlay = new PeopleItemizedOverlay(marker, MapNearby.this);
        
        JSONObject json = RequestHelper.doHttpGet("get_online_user.php", this);
        //String[] onlineUser = new String[json.length()]; 
        
        
        
        
        
        String cood[] = new String[2];
        
        try {
	        JSONObject json_data = null;
	        String x;
	        
	        for(int i=0;i<json.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = json.getJSONObject(x);		        
		        
		        cood[0] = String.valueOf(json_data.getDouble("longitude"));
		        cood[1] = String.valueOf(json_data.getDouble("latitude"));
		        addPerson(mapOverlays, cood);
		        
	        	
	        	//mails[i] = json_data.getString("mail");
	        	//names[i] = json_data.getString("name");
	        }
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mapOverlays.add(itemizedoverlay);
        
        
    }
 
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    
    private void addMe(List<Overlay> mapOverlays) {
    	Drawable markerme = this.getResources().getDrawable(R.drawable.markerme);
    	
    	// add my position
        PeopleItemizedOverlay meitemizedoverlay = new PeopleItemizedOverlay(markerme, this);
        OverlayItem meoverlayitem = new OverlayItem(geoPoint, "Me", "This is me!");
        meitemizedoverlay.addOverlay(meoverlayitem);
        mapOverlays.add(meitemizedoverlay);
    }
    
    
    private void addPerson(List<Overlay> mapOverlays, String[] coord) {
        GeoPoint point = LocationHelper.toGeoPoint(coord);
        
        OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
        
        itemizedoverlay.addOverlay(overlayitem);
    }
    
    public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
    
    
    
    public void updateLocationHandler(View button) 
    {
    	updateLocation();
    } 
    public void updateLocation()
    {
    	showCurrentLocation();
    }

    protected String[] showCurrentLocation() {

    	String[] loc = new String[2];
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            loc[0] = String.valueOf(location.getLatitude());
            loc[1] = String.valueOf(location.getLongitude());
            // Toast.makeText(Nearby.this, message, Toast.LENGTH_LONG).show();
        }
        return loc;
    }   
    
    
    
    
    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Log.v(TAG, message);

            
        
            
            //Toast.makeText(Nearby.this, message, Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            //Toast.makeText(Nearby.this, "Provider status changed", Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MapNearby.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MapNearby.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }
}