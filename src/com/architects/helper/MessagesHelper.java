package com.architects.helper;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

public class MessagesHelper 
{
	private static final String TAG = "test";
	
	public static JSONObject getConversationOverview(String user)
	{
		JSONObject j = new JSONObject();
		String params = "?type=overview&sender="+user;
        j = AccountHelper.doHttpGet("get_message.php", params);
        
        return j;
	}
	
	public static JSONObject getConversation(String user, String partner)
	{
		JSONObject j = new JSONObject();
		String params = "?type=conversation&sender="+user+"&receiver="+partner;
        j = AccountHelper.doHttpGet("get_message.php", params);
        
        return j;
	}
	
	public static String insertMessage(String sender, String receiver, String message)
	{
		JSONObject j = new JSONObject();
        try
        {
			j.put("sender", sender);
			j.put("receiver", receiver);
			j.put("message", message);

			return AccountHelper.doHttpPost("message.php", j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
}
