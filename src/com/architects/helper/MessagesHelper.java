package com.architects.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class MessagesHelper 
{
	private static final String TAG = "test";
	
	public static JSONObject getConversationOverview(String user, Context context)
	{
		JSONObject j = new JSONObject();
		String params = "?type=overview&sender="+user;
		Log.v(TAG, "XXX 0.5");
        j = RequestHelper.doHttpGet("get_message.php", context, params);
        
        return j;
	}
	
	public static JSONObject getConversation(String user, String partner, Context context)
	{
		JSONObject j = new JSONObject();
		String params = "?type=conversation&sender="+user+"&receiver="+partner;
        j = RequestHelper.doHttpGet("get_message.php", context, params);
        
        return j;
	}
	
	public static String insertMessage(String sender, String receiver, String message, Context context)
	{
		JSONObject j = new JSONObject();
        try
        {
			j.put("sender", sender);
			j.put("receiver", receiver);
			j.put("message", message);

			return RequestHelper.doHttpPost("message.php", context, j);
        }
        catch (JSONException e)
        {
        	e.printStackTrace();
        	return "";
        }
	}
}
