package com.architects.findme;

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

public class FindMe extends Activity 
{	
	public static final String PREFS_NAME = "LoginCredentials";
	public StateHelper phoneState = new StateHelper();
	private static final String TAG = "test";
	private boolean showEntry = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
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