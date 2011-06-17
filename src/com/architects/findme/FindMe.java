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
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        
        
        String loginData[] = new String[3];
        
        // save login credentials to storage
        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        loginData[0] = preferences.getString("mail", "");
        loginData[1] = preferences.getString("password", "");
        loginData[2] = preferences.getString("status", "");
        
        if (loginData[0].compareTo("") != 0)
        {
        	String response = Login.login(loginData).trim();
        	// check if login was successful
        	Log.v(TAG, response);
        	if(response.compareTo("01") == 0)
        	{
        		Intent myIntent = new Intent(this, FindMeMenu.class);
                startActivity(myIntent);
        	}
        }  
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon:     Toast.makeText(this, "You pressed the icon!", Toast.LENGTH_LONG).show();
                                break;
            case R.id.text:     Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG).show();
                                break;
            case R.id.icontext: Toast.makeText(this, "You pressed the icon and text!", Toast.LENGTH_LONG).show();
                                break;
        }
        return true;
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