package com.architects.findme;

import com.architects.findme.R;
import com.architects.findme.Login;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class FindMe extends Activity 
{	
	public static final String PREFS_NAME = "LoginCredentials";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        this.setContentView(R.layout.main);
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