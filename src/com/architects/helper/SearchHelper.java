package com.architects.helper;

import org.json.JSONObject;

import android.util.Log;

public class SearchHelper 
{
	private static final String TAG = "test";
	
	public static String[] getResults(String query)
	{
		JSONObject j = new JSONObject();
		String params = "?query="+query;
        j = AccountHelper.doHttpGet("search.php", params);
        Log.v(TAG, j.toString());
        
        String[] results = new String[j.length()]; 
        
        try {
	        JSONObject json_data = null;
	        String x;

	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(i)).toString();      	
	        	json_data = j.getJSONObject(x);
	        	
	        	results[i] = json_data.getString("name") + "\n" + json_data.getString("mail");
	        }
	        return results;
        }
        catch(Exception e) { return new String[0];}
	}
}
