package com.architects.findme;

import java.util.List;

import org.json.JSONObject;

import com.architects.helper.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Messages extends Activity {
	public static final String PREFS_NAME = "LoginCredentials";
	private static final String TAG = "findme";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_overview);

        // set title bar
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText("Conversations");
        
        // get mail
        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        String mail = preferences.getString("mail", "");
        
        // search results to string
        JSONObject j = MessagesHelper.getConversationOverview(mail);
 
        Spanned[] list = new Spanned[j.length()];
        final String[] mails = new String[j.length()];
        final String[] names = new String[j.length()];
        
        try {
	        JSONObject json_data = null;
	        String x;
	        
	        String text;
	        for(int i=0;i<j.length();i++)
	        {
	        	x = (new Integer(j.length()-i-1)).toString();      	
	        	json_data = j.getJSONObject(x);

	        	mails[i] = json_data.getString("mail");
	        	names[i] = json_data.getString("name");
	        	
	        	text = json_data.getString("message");//.substring(0, 20);
	        	list[i] = Html.fromHtml("<b>" + names[i] + "</b>" +  "<br />" + 
	                    "<small>@ " + json_data.getString("date") + "</small>");
	        }
	        
        }
        catch(Exception e) {}

        ListView messageOverview = (ListView) findViewById(R.id.messageOverviewList);
        
        messageOverview.setAdapter(new ArrayAdapter<Spanned>
    		(this, R.layout.message_overview_item, R.id.message_overview_text, list));


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
        
        
        
        
        
        
        
        
        
        
        
        
        /*
        resultsList.setAdapter(new ArrayAdapter<String>(this, R.layout.nearby_list_item, R.id.nearby_list_text, results));

        resultsList.setTextFilterEnabled(true);
        
        resultsList.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // When clicked, show a toast with the TextView text
            Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
          }
        });*/
	}
	
	public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
	
	
}