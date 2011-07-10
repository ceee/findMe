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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Login extends Activity 
{
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "test";
	private String[] loginData;
	public ProgressDialog loadingDialog;
	private String response;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(null);        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.login);
    }
	
    public void loginUserHandler(View button)
    {
    	loginData = new String[3];
    	
    	final EditText mailField = (EditText) findViewById(R.id.EditTextLoginMail);  
    	loginData[0] = mailField.getText().toString();
    	
    	final EditText passwordField = (EditText) findViewById(R.id.EditTextLoginPassword);  
    	loginData[1] = passwordField.getText().toString();

    	final Spinner statusSpinner = (Spinner) findViewById(R.id.SpinnerLoginUserStatus);  
    	loginData[2] = statusSpinner.getSelectedItem().toString(); 
    	
    	loadingDialog = ProgressDialog.show(this, null, " Loading ...", true);
		
		new Thread(new Runnable(){
			public void run(){
		    	response = AccountHelper.login(loginData, Login.this).trim();
		    	Log.v(TAG, response);
		    	
		    	
		    	// check if login was successful
		    	if(response.compareTo("01") == 0)
		    	{
		    		// save login credentials to storage
		            SharedPreferences preferences = Login.this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
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
        	if(response.compareTo("01") == 0)
        	{
        		Intent myIntent = new Intent(Login.this.getApplicationContext(), FindMeMenu.class);
	            startActivity(myIntent);
	            finish();
        	}
        	else
        		Toast.makeText(Login.this, response, Toast.LENGTH_LONG).show();
        	
        	loadingDialog.dismiss();
        }
	};
}
