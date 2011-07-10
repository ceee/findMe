package com.architects.findme;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;
import com.architects.helper.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Nearby extends TimerActivity {    
	private static final String TAG = "test";
    private ListView nearby;
    public ProgressDialog loadingDialog;
    private ArrayAdapter<Spanned> adapter;
    private Spanned[] list = null;
	private String[] mails;
	private String[] names;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.nearby);
        
        super.run();
        
        load();
	}
	
	public void reloadHandler(View v)
	{
		load();
	}	
	
	private void load()
	{ 
		loadingDialog = ProgressDialog.show(this, null, " Loading ...", true);
		
		new Thread(new Runnable(){
			public void run(){
		        String[] logPref = AccountHelper.getLoginPreferences(Nearby.this);
		        String params = "?mail=" + logPref[0];
		        JSONObject json = RequestHelper.doHttpGet("get_online_user.php", Nearby.this, params);
		        
		        list = new Spanned[json.length()]; 
		        mails = new String[json.length()];
		        names = new String[json.length()];
		        
		        try {
			        Log.v(TAG, "Länge:"+json.length());
			        JSONObject json_data = null;
			        String x;
			        
			        Location location = LocationHelper.getCurrentLocation(Nearby.this);
			        
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
			        	
			        	list[i] = Html.fromHtml(json_data.getString("name") + "<br>" + me);
			        }
						
				} catch (JSONException e) {
					Log.v(TAG, "JSONError");
				}

		        Looper.prepare();
		        uiCallback.sendEmptyMessage(0);
			}
		}).start();
	}
	
	private Handler uiCallback = new Handler() {
        @Override
        public void handleMessage(Message msg) {	
			nearby = (ListView) findViewById(R.id.nearbylist);
			
			adapter = new ArrayAdapter<Spanned>(Nearby.this, R.layout.nearby_list_item, R.id.nearby_list_text, list);
	        nearby.setAdapter(adapter);
	
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
	        
	        loadingDialog.dismiss();
        }
	};
    
    public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
