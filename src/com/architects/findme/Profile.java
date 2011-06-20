package com.architects.findme;

import org.json.JSONObject;

import com.architects.helper.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity 
{
	private static final String TAG = "findme";
	private String userName;
	private String userMail;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);        
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.profile);
        
        
        // get conversation partner
        Bundle bundle = this.getIntent().getExtras();
        userMail = bundle.getString("mail");
        userName = bundle.getString("name");
        
        Log.v(TAG, userMail+" ---"+userName);
        
        // JSONObject j = FriendsHelper.getUserInfo(mail);
        
        
        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText("Profile");
        
        // set name and mail
        TextView name = (TextView) findViewById(R.id.profilename);
        TextView mail = (TextView) findViewById(R.id.profilemail);
        
        name.setText(Html.fromHtml("<font color='#9fa6b3' size='12'><small>Name:</small></font><br />" 
				+ userName));
        mail.setText(Html.fromHtml("<font color='#9fa6b3' size='12'><small>Mail:</small></font><br />" 
				+ userMail));
    }
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	String title = item.getTitle().toString();
        switch (item.getItemId()) {
            case R.id.add:     
            	addHandler(title);
                break;
            case R.id.message:     
            	messageHandler(title);
                break;
        }
        return true;
    }
    

    public void addHandler(String title)
    {
    	String[] logPref = AccountHelper.getLoginPreferences(this);
    	String response = FriendsHelper.request("request", logPref[0], userMail).trim();
    	
    	if(response.compareTo("01") == 0)
    		response = "Request sent!";
    	Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }
    
    public void messageHandler(String title)
    {
    	Bundle bundle = new Bundle();
    	bundle.putString("mail", userMail);
    	bundle.putString("name", userName);

    	Intent newIntent = new Intent(getApplicationContext(), Conversation.class);
    	newIntent.putExtras(bundle);
    	startActivityForResult(newIntent, 0);
    }
    
    
    public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
