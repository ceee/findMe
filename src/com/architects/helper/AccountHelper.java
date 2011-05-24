package com.architects.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

import android.util.Log;

public class AccountHelper 
{
	private static final String TAG = "test";
	
	public static String doHttpPost(String url_end, JSONObject j)
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
	            return convertStreamToString(in);
	        }
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	
	public static JSONObject doHttpGet(String url_end)
	{
		String url = "http://www.artistandarchitects.at/findme/" + url_end;
		
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
	            JSONObject json=new JSONObject(convertStreamToString(in));
	            Log.i(TAG,convertStreamToString(in));  
	            return json;
	        }
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return new JSONObject();
	}
	
	
    public static String createHashMd5(String str) 
    {
        try 
        {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
            {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
            
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String convertStreamToString(InputStream is) 
    {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try 
        {
            while ((line = reader.readLine()) != null) 
            {
                sb.append(line + "\n");
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
