package com.architects.findme;

import com.architects.findme.R;
import com.architects.helper.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity 
{
	public static final String PREFS_NAME = "LoginCredentials";
	private String[] registrationData;
	public ProgressDialog loadingDialog;
	private String response;
	private String response0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        Registration.this.setContentView(R.layout.registration);
    }
	
    public void registerUserHandler(View button) 
    {
    	registrationData = new String[3];
    	
    	final EditText nameField = (EditText) findViewById(R.id.EditTextRegistrationName);  
    	registrationData[0] = nameField.getText().toString();
    	
    	final EditText mailField = (EditText) findViewById(R.id.EditTextRegistrationMail);  
    	registrationData[1] = mailField.getText().toString();
    	
    	final EditText passwordField = (EditText) findViewById(R.id.EditTextRegistrationPassword);  
    	registrationData[2] = passwordField.getText().toString();
    	
    	loadingDialog = ProgressDialog.show(this, null, " Loading ...", true);
		
		new Thread(new Runnable(){
			public void run(){
		    	String loginData[] = new String[3];
		    	
		    	loginData[0] = registrationData[1];
		    	loginData[1] = registrationData[2];
		    	loginData[2] = "online";
		    	
		    	response = AccountHelper.register(registrationData, Registration.this).trim();
		    	response0 = AccountHelper.login(loginData, Registration.this).trim();
		    	
		    	if(response.compareTo("01") == 0 && response0.compareTo("01") == 0)
		    	{
	        		// save login credentials to storage
	                SharedPreferences preferences = Registration.this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
	                SharedPreferences.Editor prefsEditor = preferences.edit();
	                prefsEditor.putString("mail", loginData[0]);
	                prefsEditor.putString("password", loginData[1]);
	                prefsEditor.putString("status", loginData[2]);
	                prefsEditor.commit();
		    	}

		    	Looper.prepare();
		        uiCallback.sendEmptyMessage(0);
			}
		}).start();
	}
	
	private Handler uiCallback = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	loadingDialog.dismiss();
        	
        	if(response.compareTo("01") != 0)
        	{
        		Toast.makeText(Registration.this, response, Toast.LENGTH_LONG).show();
        		return;
        	}
        	if(response0.compareTo("01") != 0)
        	{
        		Toast.makeText(Registration.this, response0, Toast.LENGTH_LONG).show();
        		return;
        	}
        	
    		Intent myIntent = new Intent(Registration.this.getApplicationContext(), FindMeMenu.class);
            startActivity(myIntent);
            finish();
        }
	};
}
