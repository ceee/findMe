package com.architects.findme;

import com.architects.helper.*;
import com.architects.findme.FindMeMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity 
{	
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "findme";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        boolean isLogged = AccountHelper.isLogged(this);
        Log.i(TAG, "isLogged: " + isLogged);
        
        if(isLogged)
        {
        	Intent myIntent = new Intent(this, FindMeMenu.class);
            startActivity(myIntent); 
        } else
        {
    		Intent myIntent = new Intent(this, FindMe.class);
            startActivity(myIntent);
        }
        finish();
    }
}