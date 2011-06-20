package com.architects.findme;

import com.architects.findme.R;
import com.architects.helper.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class Friends extends Activity {
	private TabHost mTabHost;
	// The two views in our tabbed example
	private ListView friends;
	private ListView requests;
	public static final String PREFS_NAME = "LoginCredentials";
	
	public String mymail;
	//private static final String TAG = "findme";

	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		mTabHost.setup();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friends);
		
		setupTabHost();

		// setup list views
		friends = (ListView) findViewById(R.id.friendslist);
		requests = (ListView) findViewById(R.id.requests);

        // get mail
        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        String mail = preferences.getString("mail", "");
        mymail = mail;
        
        String[] friendslist = FriendsHelper.getFriendsList(mail);
        String[] requestslist = FriendsHelper.getRequestsList(mail);
		friends.setAdapter(new ArrayAdapter<String>
        	(this, R.layout.nearby_list_item, R.id.nearby_list_text, friendslist));
		requests.setAdapter(new ArrayAdapter<String>
			(this, R.layout.nearby_list_item,R.id.nearby_list_text, requestslist));
        
        
		setupTab(friends, "Friends");
		setupTab(requests, "Requests");
		
		
		requests.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	  	TextView me = (TextView) view.findViewById(R.id.nearby_list_text);
	        	  	final String mail = (String) me.getText();
	        	  	
	        	    final AlertDialog.Builder alertbox = new AlertDialog.Builder(Friends.this);
		          	LayoutInflater factory = LayoutInflater.from(Friends.this);
		          	final View input = factory.inflate(R.layout.request_dialog, null);
		          	
		          	alertbox.setTitle("Friend Request");
		          	alertbox.setView(input);
	
		          	alertbox.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
		          	    public void onClick(DialogInterface dialog, int whichButton) {
		          	    	FriendsHelper.request("add", mail, mymail);
		          	    	FriendsHelper.request("decline", mail, mymail);

		          	    	Toast.makeText(Friends.this, "Friend successfully added!", Toast.LENGTH_LONG).show();
		          	    }
		          	});
	
		          	alertbox.setNegativeButton("Decline",
		          	        new DialogInterface.OnClickListener() {
		          	            public void onClick(DialogInterface dialog, int whichButton) {
				          	    	FriendsHelper.request("decline", mail, mymail);
				          	    	
				          	    	Toast.makeText(Friends.this, "Friend request declined!", Toast.LENGTH_LONG).show();
		          	            }
		          	        });
		          	alertbox.show();
	          }
		});
	}

	private void setupTab(final View view, final String tag) {
		View tabview = createTabView(mTabHost.getContext(), tag);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
			public View createTabContent(String tag) {
				return view;
			}
		});
		mTabHost.addTab(setContent);

	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	
	public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
