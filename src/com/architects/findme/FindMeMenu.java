package com.architects.findme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class FindMeMenu extends Activity 
{
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "findMe";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.findmemenu);
    }
	
	// Handler
    public void nearbyMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Nearby.class);
        startActivity(myIntent);
    }
    public void friendsMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Friends.class);
        startActivity(myIntent);
    }
    public void messagesMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Search.class);
        startActivity(myIntent);
    }
    public void meMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Search.class);
        startActivity(myIntent);
    }
    public void searchMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Search.class);
        startActivity(myIntent);
    }
    public void logoutMenuHandler(View button) 
    {
    	String logoutData[] = new String[2];
    	
    	SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
    	logoutData[0] = preferences.getString("mail", "");
    	logoutData[1] = preferences.getString("password", "");
        
        String response = Login.logout(logoutData).trim();
        Log.v(TAG, response);
        if(response.compareTo("06") == 0)
        {
        	// remove login credentials
            SharedPreferences.Editor prefsEditor = preferences.edit();
            prefsEditor.clear();
            prefsEditor.commit();
            
        	Intent myIntent = new Intent(button.getContext(), FindMe.class);
        	startActivity(myIntent);
        	Toast.makeText(button.getContext(), "Logout successful", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "Logout failed. Try again!", Toast.LENGTH_LONG).show(); 
    }
}
