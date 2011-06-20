package com.architects.findme;

import com.architects.helper.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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
        String[] results = SearchHelper.getResults(query);
        
        resultsList.setAdapter(new ArrayAdapter<String>(this, R.layout.nearby_list_item, R.id.nearby_list_text, results));

        resultsList.setTextFilterEnabled(true);
        
        resultsList.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // When clicked, show a toast with the TextView text
            Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
          }
        });
	}
	
	public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
