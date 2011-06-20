package com.architects.helper;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

public class FriendsHelper 
{
	private static final String TAG = "test";
	
	public static String[] getFriendsList(String user)
	{
		JSONObject j = new JSONObject();
		String params = "?user="+user+"&type=friends";
        j = AccountHelper.doHttpGet("get_friends.php", params);
        
        String[] friends = new String[j.length()]; 
        
        try {
	        JSONObject json_data = null;
	        String x;

	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = j.getJSONObject(x);
	        	
	        	friends[i] = json_data.getString("name") + "\n" + json_data.getString("id");
	        }
	        return friends;
        }
        catch(Exception e) { return new String[0];}
	}
	
	public static String[] getRequestsList(String user)
	{
		JSONObject j = new JSONObject();
		String params = "?user="+user+"&type=requests";
        j = AccountHelper.doHttpGet("get_friends.php", params);
		Log.v(TAG, j.toString());
        
        String[] friends = new String[j.length()]; 
        Log.v(TAG, "Länge:"+j.length());
        
        try {
	        JSONObject json_data = null;
	        String x;

	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = j.getJSONObject(x);
	        	
	        	friends[i] = json_data.getString("mail");
	        }
	        return friends;
        }
        catch(Exception e) { return new String[0];}
	}
	
	public static String request(String type, String sender, String receiver)
	{
		JSONObject j = new JSONObject();
        try
        {
			j.put("sender", sender);
			j.put("receiver", receiver);
			j.put("type", type);

			return AccountHelper.doHttpPost("friend.php", j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
	
	public static JSONObject getUserInfo(String user)
	{
		String params = "?user="+user;
        return AccountHelper.doHttpGet("get_user_info.php", params);
	}
}
