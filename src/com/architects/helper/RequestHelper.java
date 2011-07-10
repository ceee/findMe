package com.architects.helper;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class RequestHelper 
{
	public static final String PREFS_NAME = "LoginCredentials";
	
	public static String doHttpPost(String url_end, Context context, JSONObject j)
	{
		String url = "http://www.artistandarchitects.at/findme/" + url_end;
		
		HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
	    HttpResponse response;
	    
	    try
	    {
	        HttpPost post = new HttpPost(url);
	        
	        post.setHeader("json", j.toString());
	        
	        StringEntity se = new StringEntity(j.toString());
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        post.setEntity(se);
	        
	        response = client.execute(post);
	        
	        if (response != null) 
	        {
	            InputStream in = response.getEntity().getContent();
	            return StandardHelper.convertStreamToString(in);
	        }
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	public static JSONObject doHttpGet(String url_end, Context context)
	{
		return doHttpGet(url_end, context, "");
	}
	
	public static JSONObject doHttpGet(String url_end, Context context, String params)
	{
		String url = "http://www.artistandarchitects.at/findme/" + url_end;
		
		if(params != "") url += params;
		
		HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
	    HttpResponse response;
	    
	    try
	    {
	        HttpGet httpGet = new HttpGet(url);
	        
	        response = client.execute(httpGet);
	        if (response != null) 
	        {
	            InputStream in = response.getEntity().getContent();
	            JSONObject json=new JSONObject(StandardHelper.convertStreamToString(in));
	            Log.i("HttpGet Response: ", StandardHelper.convertStreamToString(in));  
	            return json;
	        }
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return new JSONObject();
	}
	
	public boolean isOnline(Context c) {
	    ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	    return cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
