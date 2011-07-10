package com.architects.findme;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.architects.findme.R;
import com.architects.helper.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class Friends extends Activity {
	private TabHost mTabHost;
	private ListView friends;
	private ListView requests;
	public static final String PREFS_NAME = "LoginCredentials";
	private String mail = null;
	public ProgressDialog loadingDialog;
	private ArrayAdapter<Spanned> friendsAdapter;
	private ArrayAdapter<Spanned> requestsAdapter;
	private ArrayList<Spanned> friendslist = null;
	private ArrayList<Spanned> requestslist = null;
	
	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		mTabHost.setup();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friends);
		
		// get mail
        SharedPreferences preferences = this.getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        mail = preferences.getString("mail", "");
		
		load();
	}
	
	private void load()
	{
		loadingDialog = ProgressDialog.show(this, null, " Loading ...", true);
		
		new Thread(new Runnable(){
			public void run(){
				setupTabHost();
		
				// setup list views
				friends = (ListView) findViewById(R.id.friendslist);
				requests = (ListView) findViewById(R.id.requests);
		        
		        friendslist = FriendsHelper.getFriendsList(mail, Friends.this);
		        requestslist = FriendsHelper.getRequestsList(mail, Friends.this);
		        
		        Looper.prepare();
		        uiCallback.sendEmptyMessage(0);
			}
		}).start();
	}
	
	private Handler uiCallback = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        		
        	friendsAdapter = new ArrayAdapter<Spanned>
				(Friends.this, R.layout.nearby_list_item, R.id.nearby_list_text, friendslist);
        	friends.setAdapter(friendsAdapter);
        	
        	requestsAdapter = new ArrayAdapter<Spanned>
				(Friends.this, R.layout.nearby_list_item, R.id.nearby_list_text, requestslist);
	    	requests.setAdapter(requestsAdapter);
	        
			setupTab(friends, "Friends");
			setupTab(requests, "Requests");
			
			requests.setOnItemClickListener(new OnItemClickListener() {
		          public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		        	  	final String mymail = FriendsHelper.mails[position];
		        	  	
		        	    final AlertDialog.Builder alertbox = new AlertDialog.Builder(Friends.this);
			          	LayoutInflater factory = LayoutInflater.from(Friends.this);
			          	final View input = factory.inflate(R.layout.request_dialog, null);
			          	
			          	alertbox.setTitle("Friend Request");
			          	alertbox.setView(input);
		
			          	alertbox.setPositiveButton("Accept", 
			          		new DialogInterface.OnClickListener() {
				          	    public void onClick(DialogInterface dialog, int whichButton) {
				          	    	String response = FriendsHelper.request("add", mymail, mail, Friends.this).trim();
				          	    	
			          	    		if(response.compareTo("01") == 0)
			          	    		{
			          	    			requestslist.remove(position);
					            		requestsAdapter.notifyDataSetChanged();
					            		
					            		JSONObject userInfo = FriendsHelper.getUserInfo(mymail, Friends.this);
					            		
					            		try {
											friendslist.add(Html.fromHtml
												(userInfo.getString("name") + "<br>" + userInfo.getString("uid")));
										} catch (JSONException e) {}
					            		friendsAdapter.notifyDataSetChanged();
					            		
					            		Toast.makeText(Friends.this, "Friend successfully added!", Toast.LENGTH_LONG).show();
			          	    		}
			          	    		else
			          	    			Toast.makeText(Friends.this, "Error", Toast.LENGTH_LONG).show();
				          	    }
				          	});
		
			          	alertbox.setNegativeButton("Decline",
		          	        new DialogInterface.OnClickListener() {
		          	            public void onClick(DialogInterface dialog, int whichButton) {
		          	            	String response = FriendsHelper.request("decline", mymail, mail, Friends.this).trim();
				          	    	
		          	            	if(response.compareTo("01") == 0)
			          	    		{
					          	    	requestslist.remove(position);
					            		requestsAdapter.notifyDataSetChanged();
				          	    	
					            		Toast.makeText(Friends.this, "Friend request declined!", Toast.LENGTH_LONG).show();
			          	    		}
		          	            	else
		          	            		Toast.makeText(Friends.this, "Error", Toast.LENGTH_LONG).show();
		          	            }
		          	        });
			          	alertbox.show();
		          }
			});
	        
        	loadingDialog.dismiss();
        }
	};

	private void setupTab(final View view, final String tag) {
		View tabview = createTabView(mTabHost.getContext(), tag);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
			public View createTabContent(String tag) {
				return view;
			}
		});
		mTabHost.addTab(setContent);

	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	
	public void enterMenuHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), FindMeMenu.class);
        startActivity(myIntent);
    }
}
