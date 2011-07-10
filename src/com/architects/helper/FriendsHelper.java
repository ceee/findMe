package com.architects.helper;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class FriendsHelper 
{
	private static final String TAG = "test";
	public static String[] mails = null;
	
	public static ArrayList<Spanned> getFriendsList(String user, Context context)
	{
		JSONObject j = new JSONObject();
		String params = "?user="+user+"&type=friends";
        j = RequestHelper.doHttpGet("get_friends.php", context, params);
        
        ArrayList<Spanned> friends = new ArrayList<Spanned>();
        mails = new String[j.length()];
        
        try {
	        JSONObject json_data = null;
	        String x;

	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = j.getJSONObject(x);
	        	
	        	friends.add(Html.fromHtml(json_data.getString("name") + "<br>" + json_data.getString("id")));
	        }
	        return friends;
        }
        catch(Exception e) { return new ArrayList<Spanned>();}
	}
	
	public static ArrayList<Spanned> getRequestsList(String user, Context context)
	{
		JSONObject j = new JSONObject();
		String params = "?user="+user+"&type=requests";
        j = RequestHelper.doHttpGet("get_friends.php", context, params);
		Log.v(TAG, j.toString());
        
		ArrayList<Spanned> friends = new ArrayList<Spanned>(); 
        Log.v(TAG, "Länge:"+j.length());
        
        try {
	        JSONObject json_data = null;
	        String x;

	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = j.getJSONObject(x);

	        	mails[i] = json_data.getString("mail");
	        	
	        	friends.add(Html.fromHtml(json_data.getString("mail")));
	        }
	        return friends;
        }
        catch(Exception e) { 
        	e.printStackTrace();
        	return new ArrayList<Spanned>();
        }
	}
	
	public static String request(String type, String sender, String receiver, Context context)
	{
		JSONObject j = new JSONObject();
        try
        {
			j.put("sender", sender);
			j.put("receiver", receiver);
			j.put("type", type);

			return RequestHelper.doHttpPost("friend.php", context, j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
	
	public static JSONObject getUserInfo(String user, Context context)
	{
		String params = "?user="+user;
        JSONObject j = RequestHelper.doHttpGet("get_user_info.php", context, params);
        Log.v(TAG, j.toString());
        return j;
        //return j.g;
	}
}
