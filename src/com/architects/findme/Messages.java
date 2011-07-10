package com.architects.findme;

import org.json.JSONObject;

import com.architects.helper.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Messages extends Activity {
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "findme";
	public ProgressDialog loadingDialog;
	private String mail = null;
	private ListView messageOverview = null;
	private Spanned[] list = null;
	private String[] mails;
	private String[] names;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_overview);
        
        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText("Conversations");
        
        load();
	}
	
	public void enterMenuHandler(View v) 
    {
    	Intent myIntent = new Intent(v.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
	
	public void reloadHandler(View v)
	{
		load();
	}	
	
	private void load()
	{
		loadingDialog = ProgressDialog.show(this, null, " Loading ...", true);
		
		new Thread(new Runnable(){
			public void run(){
				// get mail
				String[] logPref = AccountHelper.getLoginPreferences(Messages.this);
				mail = logPref[0];
		        
				messageOverview = (ListView) findViewById(R.id.messageOverviewList);
				
				// search results to string
				Log.v(TAG, "XXX 00");
		        JSONObject j = MessagesHelper.getConversationOverview(mail, Messages.this);
		 
		        list = new Spanned[j.length()];
		        mails = new String[j.length()];
		        names = new String[j.length()];
		        
		        try {
			        JSONObject json_data = null;
			        String x;

			        for(int i=0;i<j.length();i++)
			        {
			        	x = (new Integer(j.length()-i-1)).toString();      	
			        	json_data = j.getJSONObject(x);
		
			        	mails[i] = json_data.getString("mail");
			        	names[i] = json_data.getString("name");

			        	list[i] = Html.fromHtml("<b>" + names[i] + "</b>" +  "<br />" + 
			                    "<small>" + json_data.getString("date") + "</small>");
			        }
			        
		        }
		        catch(Exception e) {}

		        Looper.prepare();
		        uiCallback.sendEmptyMessage(0);
			}
		}).start();
	}
	
	private Handler uiCallback = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	messageOverview.setAdapter(new ArrayAdapter<Spanned>
	    		(Messages.this, R.layout.message_overview_item, R.id.message_overview_text, list));
	        
	        messageOverview.setTextFilterEnabled(true);
	        
	        messageOverview.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	  	    	Bundle bundle = new Bundle();
	  	    	bundle.putString("mail", mails[position]);
	  	    	bundle.putString("name", names[position]);
	
	  	    	Intent newIntent = new Intent(getApplicationContext(), Conversation.class);
	  	    	newIntent.putExtras(bundle);
	  	    	startActivityForResult(newIntent, 0);
	          }
	        });
	        
        	loadingDialog.dismiss();
        }
	};	
}