package com.architects.findme;

import java.util.ArrayList;
import java.util.List;

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
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Conversation extends Activity {
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "findme";
	
	private String sender;
	private String receiver;
	
	private ListView messageOverview;
	
	private ArrayAdapter<Spanned> adapter;
	ArrayList<Spanned> list=new ArrayList<Spanned>();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.conversation);
        
        // get conversation partner
        Bundle bundle = this.getIntent().getExtras();
        String partnerMail = bundle.getString("mail");
        receiver = partnerMail;
        String partnerName = bundle.getString("name");
        
        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(Html.fromHtml("Conversation with <b>" + partnerName + "</b>"));

        // get mail
        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        String mail = preferences.getString("mail", "");
        sender = mail;
        
        // search results to string
        JSONObject j = MessagesHelper.getConversation(mail, partnerMail);
        
        try {
	        JSONObject json_data = null;
	        String x;
	        
	        
	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(j.length()-i-1)).toString();      	
	        	json_data = j.getJSONObject(x);
	        	
	        	if(json_data.getString("sendermail").compareTo(mail) == 0)
	        		list.add(Html.fromHtml("<font color='#9fa6b3'><small>Ich:</small></font><br />" 
	        				+ json_data.getString("message")));
	        	else
	        		list.add(Html.fromHtml("<font color='#9fa6b3'><small>"+json_data.getString("name")
	        				+":</small></font><br />" +
	        				"<font color='#2a78b9'>" + json_data.getString("message") + "</font>"));
	        }
	        
        }
        catch(Exception e) {}

        messageOverview = (ListView) findViewById(R.id.messageOverviewList);
        
        adapter = new ArrayAdapter<Spanned>
		(this, R.layout.messsage_item, R.id.message_text, list);
        messageOverview.setAdapter(adapter);
	}
	
	
	public void messageSubmitHandler(View v)
	{
		View form = (View) v.getParent();
		
		// get the input value
    	EditText mText = (EditText) form.findViewById(R.id.messageinput);
    	String text = mText.getText().toString();
    	
    	// hide soft keyboard
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mText.getWindowToken(), 0);
    	
    	String response = MessagesHelper.insertMessage(this.sender, this.receiver, text);
    	
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