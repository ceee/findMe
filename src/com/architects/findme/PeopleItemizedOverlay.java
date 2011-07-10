package com.architects.findme;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.architects.helper.*;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class PeopleItemizedOverlay extends ItemizedOverlay 
{
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private String[] mails;
	private String[] names;
	
	public PeopleItemizedOverlay(Drawable defaultMarker, Context context, String[] names, String[] mails) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		this.mails = mails;
		this.names = names;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	
	@Override
	protected boolean onTap(int index) {
	  //OverlayItem item = mOverlays.get(index);
	  Log.v("test", "ID " + index);
	  Bundle bundle = new Bundle();
	  bundle.putString("mail", mails[index]);
	  bundle.putString("name", names[index]);
	  
	  Intent newIntent = new Intent(mContext.getApplicationContext(), Profile.class);
	  newIntent.putExtras(bundle);
	  mContext.startActivity(newIntent);
	  
	  return true;
	}
	
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

}
