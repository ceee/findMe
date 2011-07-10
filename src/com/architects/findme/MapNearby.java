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
    
	private MapController mapController;
	private List<Overlay> mapOverlays;
	private static final String TAG = "MapNearby";
	private MapView mapView;
	private GeoPoint geoPoint;
	private PeopleItemizedOverlay itemizedoverlay;
	protected LocationManager locationManager;
	private String[] mails;
	private String[] names;
	
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
        Location location = LocationHelper.getCurrentLocation(this);
        String coordinates[] = new String[2];
        coordinates[0] = String.valueOf(location.getLatitude());
        coordinates[1] = String.valueOf(location.getLongitude());
        geoPoint = LocationHelper.toGeoPoint(coordinates);
        mapController.animateTo(geoPoint);
        mapController.setZoom(standardZoom); 
        mapView.invalidate();
        
        // show my marker
        Drawable marker = MapNearby.this.getResources().getDrawable(R.drawable.marker);
        addMe(mapOverlays);
        
        JSONObject json = RequestHelper.doHttpGet("get_online_user.php", this, "?mail="+AccountHelper.getMail(this));
        //String[] onlineUser = new String[json.length()]; 
        
        
        String cood[] = new String[2];
        mails = new String[json.length()];
        names = new String[json.length()];
        
        JSONObject json_data = null;
        String x;
        
        try {
	        for(int i=0;i<json.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = json.getJSONObject(x);	
	        	
	        	mails[i] = json_data.getString("mail");
	        	names[i] = json_data.getString("name");
	        }	
		} catch (JSONException e) {
			Log.v(TAG, "JSONError");
		}
		
		// show people marker
        itemizedoverlay = new PeopleItemizedOverlay(marker, MapNearby.this, names, mails);
		
        
        try {
	        json_data = null;
	        x = null;
	        
	        for(int i=0;i<json.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = json.getJSONObject(x);		        
		        
		        cood[0] = String.valueOf(json_data.getDouble("latitude"));
		        cood[1] = String.valueOf(json_data.getDouble("longitude"));
		        addPerson(mapOverlays, cood);
	        }	
		} catch (JSONException e) {
			Log.v(TAG, "JSONError");
		}
	
        
        mapOverlays.add(itemizedoverlay);
        
        
    }
 
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    
    private void addMe(List<Overlay> mapOverlays) {
    	Drawable markerme = this.getResources().getDrawable(R.drawable.markerme);
    	
    	JSONObject j = FriendsHelper.getUserInfo(AccountHelper.getMail(this), this);
		
		String[] name = new String[1];
		String[] mail = new String[1];
		try {
			name[0] = j.getString("name");
			mail[0] = j.getString("mail");
		} catch (JSONException e) {
			Log.v(TAG, "JSONError");
		}
		
    	
    	// add my position
        PeopleItemizedOverlay meitemizedoverlay = new PeopleItemizedOverlay(markerme, this, name, mail);
        OverlayItem meoverlayitem = new OverlayItem(geoPoint, "Me", "This is me!");
        meitemizedoverlay.addOverlay(meoverlayitem);
        mapOverlays.add(meitemizedoverlay);
    }
    
    
    private void addPerson(List<Overlay> mapOverlays, String[] coord) {
        GeoPoint point = LocationHelper.toGeoPoint(coord);
        
        OverlayItem overlayitem = new OverlayItem(point, "Hola!", "I'm here!");
        
        itemizedoverlay.addOverlay(overlayitem);
    }
    
    public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}