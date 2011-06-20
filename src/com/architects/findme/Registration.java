package com.architects.findme;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;
import com.architects.findme.R.id;
import com.architects.findme.R.layout;
import com.architects.helper.AccountHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity 
{
	public static final String PREFS_NAME = "LoginCredentials";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        Registration.this.setContentView(R.layout.registration);
    }

    
	public String register(String[] str)
	{
        JSONObject j = new JSONObject();
        try
        {
	        j.put("name", str[0]);
			j.put("mail", str[1]);
			j.put("password", AccountHelper.createHashMd5(str[2]));
			
			return AccountHelper.doHttpPost("register_user.php", j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}

	
    public void registerUserHandler(View button) 
    {
    	String registrationData[] = new String[3];
    	
    	final EditText nameField = (EditText) findViewById(R.id.EditTextRegistrationName);  
    	registrationData[0] = nameField.getText().toString();
    	
    	final EditText mailField = (EditText) findViewById(R.id.EditTextRegistrationMail);  
    	registrationData[1] = mailField.getText().toString();
    	
    	final EditText passwordField = (EditText) findViewById(R.id.EditTextRegistrationPassword);  
    	registrationData[2] = passwordField.getText().toString();
    	
    	String loginData[] = new String[3];
    	
    	loginData[0] = registrationData[1];
    	loginData[1] = registrationData[2];
    	loginData[2] = "online";
    	
    	String response = register(registrationData).trim();
    	
    	if(response.compareTo("01") == 0)
    	{
    		response = Login.login(loginData).trim();
        	
        	
        	// check if login was successful
        	if(response.compareTo("01") == 0)
        	{
        		// save login credentials to storage
                SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
                SharedPreferences.Editor prefsEditor = preferences.edit();
                prefsEditor.putString("mail", loginData[0]);
                prefsEditor.putString("password", loginData[1]);
                prefsEditor.putString("status", loginData[2]);
                prefsEditor.commit();
        		
        		Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
                startActivity(myIntent);
                finish();
        	}
        	else
        		Toast.makeText(this, response, Toast.LENGTH_LONG).show(); 
    	}
    	else
    		Toast.makeText(this, response, Toast.LENGTH_LONG).show();  
    }
    
}
