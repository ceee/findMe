package com.architects.findme;

import org.json.JSONObject;

import com.architects.helper.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Search extends Activity {
	private ListView resultsList;
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "findme";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        
        // get search query
        Bundle bundle = this.getIntent().getExtras();
        String query = bundle.getString("query");
        
        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(Html.fromHtml("Results for <b>"+query+"</b>"));
        
        // get results list view
        resultsList = (ListView) findViewById(R.id.searchList);
        
        // search results to string
        JSONObject j = new JSONObject();
		String params = "?query="+query;
        j = AccountHelper.doHttpGet("search.php", params);
        Log.v(TAG, j.toString());
        
        final String[] mails = new String[j.length()];
        final String[] names = new String[j.length()];
        
        String[] results = new String[j.length()]; 
        
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

        }
        catch(Exception e) { }
        
        resultsList.setAdapter(new ArrayAdapter<String>(this, R.layout.nearby_list_item, R.id.nearby_list_text, results));

        resultsList.setTextFilterEnabled(true);
        
        resultsList.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  Bundle bundle = new Bundle();
		    	bundle.putString("mail", mails[position]);
		    	bundle.putString("name", names[position]);
	
		    	Intent newIntent = new Intent(getApplicationContext(), Profile.class);
		    	newIntent.putExtras(bundle);
		    	startActivity(newIntent);
          }
        });
	}
	
	public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
