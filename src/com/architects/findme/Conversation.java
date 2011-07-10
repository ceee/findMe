package com.architects.findme;

import java.util.ArrayList;
import org.json.JSONObject;

import com.architects.helper.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Conversation extends Activity {
	public static final String PREFS_NAME = "LoginCredentials";
	private String sender;
	private String receiver;
	private ListView messageOverview;
	private ProgressDialog loadingDialog = null;
	
	private ArrayAdapter<Spanned> adapter;
	ArrayList<Spanned> list= null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.conversation);
        
        // get conversation partner
        Bundle bundle = this.getIntent().getExtras();
        receiver = bundle.getString("mail");
        
        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(Html.fromHtml("Conversation with <b>" + bundle.getString("name") + "</b>"));
        
        // get mail
        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        sender = preferences.getString("mail", "");
        
        load();
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
				
				list = new ArrayList<Spanned>();
				
				// search results to string
		        JSONObject j = MessagesHelper.getConversation(sender, receiver, Conversation.this);
		        
		        try {
			        JSONObject json_data = null;
			        String x;
			        
			        for(int i=0;i<j.length();i++)
			        {
			        	x = (new Integer(j.length()-i-1)).toString();      	
			        	json_data = j.getJSONObject(x);
			        	
			        	if(json_data.getString("sendermail").compareTo(sender) == 0)
			        		list.add(Html.fromHtml("<font color='#9fa6b3'><small>Ich:</small></font><br />" 
			        				+ json_data.getString("message")));
			        	else
			        		list.add(Html.fromHtml("<font color='#9fa6b3'><small>"+json_data.getString("name")
			        				+":</small></font><br />" +
			        				"<font color='#2a78b9'>" + json_data.getString("message") + "</font>"));
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
        	messageOverview = (ListView) findViewById(R.id.messageOverviewList);
	        
	        adapter = new ArrayAdapter<Spanned>
				(Conversation.this, R.layout.messsage_item, R.id.message_text, list);
	        messageOverview.setAdapter(adapter);
	        
	        
        	loadingDialog.dismiss();
        }
	};
	
	public void messageSubmitHandler(View v)
	{
		View form = (View) v.getParent();
		//v.getId();
		// get the input value
    	EditText mText = (EditText) form.findViewById(R.id.messageinput);
    	String text = mText.getText().toString();
    	
    	// hide soft keyboard
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mText.getWindowToken(), 0);
    	
    	String response = MessagesHelper.insertMessage(this.sender, this.receiver, text, this);
    	
    	// handle response
    	if(response.trim().compareTo("01") != 0)
    		Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
    	else
    	{
    		Spanned mys = Html.fromHtml("<font color='#9fa6b3'><small>Ich:</small></font><br />" + text);
    		list.add(0, mys);
    		adapter.notifyDataSetChanged();
    		Toast.makeText(getApplicationContext(), "Successfully sent!", Toast.LENGTH_SHORT).show();
    	}
	}
	
	public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
	
	
}