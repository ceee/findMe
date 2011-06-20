package com.architects.findme;

import com.architects.helper.*;
import com.architects.findme.R;
import com.architects.helper.StateHelper;
import com.architects.findme.Login;
import com.architects.findme.FindMeMenu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class Main extends Activity 
{	
	public static final String PREFS_NAME = "LoginCredentials";
	public StateHelper phoneState = new StateHelper();
	private static final String TAG = "test";
	private boolean showEntry = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        boolean isLogged = AccountHelper.isLogged(this);
        
        if(isLogged)
        {
        	String[] loginCredentials = AccountHelper.getLoginPreferences(this);
        	/*
        	String response = Login.login(loginCredentials).trim();
        	// check if login was successful
        	Log.v(TAG, response);
        	if(response.compareTo("01") == 0)
        	{
        		Intent myIntent = new Intent(this, FindMeMenu.class);
                startActivity(myIntent); 
        	}*/
        	Intent myIntent = new Intent(this, FindMeMenu.class);
            startActivity(myIntent); 
        }
        else
        {
    		Intent myIntent = new Intent(this, FindMe.class);
            startActivity(myIntent);
        }
        finish();
    }
    
    // VIEWS
    public void drawRegistrationHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Registration.class);
        startActivity(myIntent);
    }

    public void drawLoginHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Login.class);
        startActivity(myIntent);
    }
    

}