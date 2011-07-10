package com.architects.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

public class AccountHelper
{
	public static final String PREFS_NAME = "LoginCredentials";
	
	public static Boolean isLogged(Context c) 
	{
		SharedPreferences preferences = c.getSharedPreferences(PREFS_NAME, 0);
        String mail = preferences.getString("mail", "");    
        
        if (mail.compareTo("") != 0) return true;
        else return false;
	}
	
	public static String[] getLoginPreferences(Context c) 
	{
		String[] loginPreferences = new String[3];
		
		SharedPreferences preferences = c.getSharedPreferences(PREFS_NAME, 0);
		
		loginPreferences[0] = preferences.getString("mail", ""); 
		loginPreferences[1] = preferences.getString("password", ""); 
		loginPreferences[2] = preferences.getString("status", ""); 
		
		return loginPreferences;
	}
	
	public static String getMail(Context c)
	{
		SharedPreferences preferences = c.getSharedPreferences(PREFS_NAME, 0);
		
		return preferences.getString("mail", ""); 
	}
	
	public static Boolean clearLoginPreferences(Context c) 
	{
		SharedPreferences preferences = c.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor prefsEditor = preferences.edit();   
        
		prefsEditor.clear();
		prefsEditor.commit();
		
        return (isLogged(c)) ? false : true;
	}
	
	public static String login(String[] str, Context context)
	{
		JSONObject j = new JSONObject();
        try
        {
        	Location loc = LocationHelper.getCurrentLocation(context);
			j.put("mail", str[0]);
			j.put("password", StandardHelper.createHashMd5(str[1]));
			j.put("status", str[2]);
			
			j.put("longitude", loc.getLongitude());
			j.put("latitude", loc.getLatitude());

			return RequestHelper.doHttpPost("login_user.php", context, j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
	
	public static String logout(Context context)
	{
		String str[] = getLoginPreferences(context);
		
		JSONObject j = new JSONObject();
        
        try
        {
			j.put("mail", str[0]);
			j.put("password", StandardHelper.createHashMd5(str[1]));
			j.put("logout", "01");

			return RequestHelper.doHttpPost("login_user.php", context, j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
	
	
	public static String register(String[] str, Context context)
	{
        JSONObject j = new JSONObject();
        try
        {
	        j.put("name", str[0]);
			j.put("mail", str[1]);
			j.put("password", StandardHelper.createHashMd5(str[2]));
			
			return RequestHelper.doHttpPost("register_user.php", context, j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
}
