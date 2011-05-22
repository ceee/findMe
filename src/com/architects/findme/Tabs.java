package com.architects.findme;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html.TagHandler;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabWidget;

public class Tabs extends TabActivity {
	private static final String TAG = "test";
	
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "sicher");
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tabs);
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
	    Log.v(TAG, "01");

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, Friends.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("friends").setIndicator("Friends").setContent(intent);
	    tabHost.addTab(spec);
	    
	    Log.v(TAG, "01.5");

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, Chat.class);
	    spec = tabHost.newTabSpec("chat").setIndicator("Chat").setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, Search.class);
	    spec = tabHost.newTabSpec("search").setIndicator("Search").setContent(intent);
	    tabHost.addTab(spec);
	    
	    Log.v(TAG, "02");

	    tabHost.setCurrentTab(2);
	    
	    Log.v(TAG, "03");
	}
}
