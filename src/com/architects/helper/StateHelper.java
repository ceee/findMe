package com.architects.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;


public class StateHelper extends Activity 
{
    public String isOnline() 
    {
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 if (cm.getActiveNetworkInfo().isConnectedOrConnecting())
			 return "CONNECTED";
		 else return "NOT CONNECTED";
	}
}
