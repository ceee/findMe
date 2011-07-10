package com.architects.findme;

import com.architects.helper.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Search extends Activity {
	private ListView resultsList;
	public static final String PREFS_NAME = "LoginCredentials";
	private String query = null;
	private ProgressDialog loadingDialog = null;
	private String[] results = null;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        
        // get search query
        Bundle bundle = this.getIntent().getExtras();
        query = bundle.getString("query");
        
        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(Html.fromHtml("Results for <b>"+query+"</b>"));
        
        loadingDialog = ProgressDialog.show(this, null, " Loading ...", true);
        
        new Thread(new Runnable(){
			public void run(){
		        String[] logPref = AccountHelper.getLoginPreferences(Search.this);
		        results = SearchHelper.getResults(query, logPref[0], Search.this);
		        
		        Looper.prepare();
		        uiCallback.sendEmptyMessage(0);
			}
		}).start();
	}
	
	private Handler uiCallback = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	
        	// get results list view
	        resultsList = (ListView) findViewById(R.id.searchList);
        	
        	resultsList.setAdapter(new ArrayAdapter<String>
        		(Search.this, R.layout.nearby_list_item, R.id.nearby_list_text, results));

            resultsList.setTextFilterEnabled(true);
            
            // view Profile if list item clicked
            resultsList.setOnItemClickListener(new OnItemClickListener() {
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	  Bundle bundle = new Bundle();
    		    	bundle.putString("mail", SearchHelper.mails[position]);
    		    	bundle.putString("name", SearchHelper.names[position]);
    	
    		    	Intent newIntent = new Intent(getApplicationContext(), Profile.class);
    		    	newIntent.putExtras(bundle);
    		    	startActivity(newIntent);
              }
            });
	        
        	loadingDialog.dismiss();
        }
	};
	
	public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
