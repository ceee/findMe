package com.architects.findme;

import com.architects.helper.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class FindMeMenu extends TimerActivity 
{
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "FindMeMenu";
	
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        this.setContentView(R.layout.findmemenu);
        
        super.run();
    }
	
	// Handler
    public void nearbyMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), MapNearby.class);
        startActivity(myIntent);
    }
    public void nearbyListMenuHandler(View button) 
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
    	String[] loginCredentials = AccountHelper.getLoginPreferences(this);
    	
    	Bundle bundle = new Bundle();
    	bundle.putString("mail", loginCredentials[0]);
    	bundle.putString("name", "Tobias Klika");

    	Intent newIntent = new Intent(getApplicationContext(), Profile.class);
    	newIntent.putExtras(bundle);
    	startActivity(newIntent);
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
        String response = AccountHelper.logout(this).trim();
        Log.v(TAG, response);
        if(response.compareTo("06") == 0)
        {
        	if(AccountHelper.clearLoginPreferences(this))
        	{
	            Toast.makeText(button.getContext(), "Logout successful", Toast.LENGTH_LONG).show();
	            finish();
	            Intent myIntent = new Intent(button.getContext(), Main.class);
	        	startActivity(myIntent);
        	}
        }
        else Toast.makeText(this, "Logout failed. Try again!", Toast.LENGTH_LONG).show(); 
    }
    
    public void searchHandler(View button)
    {
    	Intent myIntent = new Intent(button.getContext(), Search.class);
        startActivity(myIntent);
    }
}
