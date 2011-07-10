package com.architects.helper;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class SearchHelper 
{
	private static final String TAG = "findme";
	public static String[] mails = null;
	public static String[] names = null;
	
	public static String[] getResults(String query, String mail, Context context)
	{
		JSONObject j = new JSONObject();
		String params = "?query="+query+"&mail="+mail;
        j = RequestHelper.doHttpGet("search.php", context, params);
        Log.v(TAG, j.toString());
        
        String[] results = new String[j.length()]; 
        mails = new String[j.length()];
        names = new String[j.length()];
        
        try {
	        JSONObject json_data = null;
	        String x;

	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = j.getJSONObject(x);
	        	
	        	mails[i] = json_data.getString("mail");
	        	names[i] = json_data.getString("name");
	        	
	        	results[i] = json_data.getString("name") + "\n" + json_data.getString("mail");
	        }
	        return results;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return new String[0];
        }
	}
}
