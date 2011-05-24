package com.architects.findme;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;
import com.architects.findme.R.id;
import com.architects.findme.R.layout;
import com.architects.helper.AccountHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity 
{
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.registration);
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
    	
    	String response = register(registrationData);
    	Toast.makeText(this, response, Toast.LENGTH_LONG).show();  
    }
    
}
