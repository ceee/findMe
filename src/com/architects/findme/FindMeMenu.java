package com.architects.findme;

import com.architects.resources.*;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FindMeMenu extends Activity 
{
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "findMe";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.findmemenu);
    }
	
	// Handler
    public void nearbyMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Nearby.class);
        startActivity(myIntent);
    }
    public void friendsMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Friends.class);
        startActivity(myIntent);
    }
    public void messagesMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Messages.class);
        startActivity(myIntent);
    }
    public void meMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Search.class);
        startActivity(myIntent);
    }
    public void searchMenuHandler(View button) 
    {
    	final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
    	LayoutInflater factory = LayoutInflater.from(this);
    	final View input = factory.inflate(R.layout.search_dialog, null);
    	
    	alertbox.setTitle("Search People");
    	alertbox.setView(input);

    	alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {
    	    	// get the input value
    	    	EditText mText;
    	    	mText = (EditText) input.findViewById(R.id.SearchInput);
    	    	String text = mText.getText().toString();
    	    	
    	    	// redirect to search results
    	    	Bundle bundle = new Bundle();
    	    	bundle.putString("query", text);

    	    	Intent newIntent = new Intent(FindMeMenu.this.getApplicationContext(), Search.class);
    	    	newIntent.putExtras(bundle);
    	    	startActivityForResult(newIntent, 0);
    	    }
    	});

    	alertbox.setNegativeButton("Cancel",
    	        new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog, int whichButton) {
    	                dialog.cancel();
    	            }
    	        });
    	alertbox.show();
    }
    public void logoutMenuHandler(View button) 
    {
    	String logoutData[] = new String[2];
    	
    	SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
    	logoutData[0] = preferences.getString("mail", "");
    	logoutData[1] = preferences.getString("password", "");
        
        String response = Login.logout(logoutData).trim();
        Log.v(TAG, response);
        if(response.compareTo("06") == 0)
        {
        	// remove login credentials
            SharedPreferences.Editor prefsEditor = preferences.edit();
            prefsEditor.clear();
            prefsEditor.commit();
            
            Toast.makeText(button.getContext(), "Logout successful", Toast.LENGTH_LONG).show();
        	Intent myIntent = new Intent(button.getContext(), FindMe.class);
        	startActivity(myIntent);
        	
        }
        else Toast.makeText(this, "Logout failed. Try again!", Toast.LENGTH_LONG).show(); 
    }
    
    public void searchHandler(View button)
    {
    	Intent myIntent = new Intent(button.getContext(), Search.class);
        startActivity(myIntent);
    }
}
