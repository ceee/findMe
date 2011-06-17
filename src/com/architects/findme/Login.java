package com.architects.findme;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;
import com.architects.helper.AccountHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);        
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.login);
    }
	
	public static String login(String[] str)
	{
		JSONObject j = new JSONObject();
        try
        {
			j.put("mail", str[0]);
			j.put("password", AccountHelper.createHashMd5(str[1]));
			j.put("status", str[2]);
			
			j.put("longitude", -122.084095);
			j.put("latitude", 37.422006);

			return AccountHelper.doHttpPost("login_user.php", j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
	
	
    public void loginUserHandler(View button)
    {
    	String loginData[] = new String[3];
    	
    	final EditText mailField = (EditText) findViewById(R.id.EditTextLoginMail);  
    	loginData[0] = mailField.getText().toString();
    	
    	final EditText passwordField = (EditText) findViewById(R.id.EditTextLoginPassword);  
    	loginData[1] = passwordField.getText().toString();

    	final Spinner statusSpinner = (Spinner) findViewById(R.id.SpinnerLoginUserStatus);  
    	loginData[2] = statusSpinner.getSelectedItem().toString(); 
    	
    	String response = login(loginData).trim();
    	Log.v(TAG, response);
    	
    	
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
    	}
    	else Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }

}
