package com.architects.findme;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class PeopleItemizedOverlay extends ItemizedOverlay 
{
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private static final String TAG = "findMe";

	public PeopleItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
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
	  OverlayItem item = mOverlays.get(index);
	  
	  Bundle bundle = new Bundle();
	  bundle.putString("mail", "klika@live.at");
	  bundle.putString("name", "Tobias Klika");
	  
	  Intent newIntent = new Intent(mContext.getApplicationContext(), Profile.class);
	  newIntent.putExtras(bundle);
	  mContext.startActivity(newIntent);
	  
	  return true;
	  
	  /*
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  
	  LayoutInflater factory = LayoutInflater.from(mContext);
  	  final View input = factory.inflate(R.layout.map_dialog, null);
	  dialog.setView(input);
	  
	  
	  dialog.setPositiveButton("Profile", new DialogInterface.OnClickListener() {
	  	    public void onClick(DialogInterface dialog, int whichButton) {
	  	    	// get the input value
	  	    	EditText mText;
	  	    	mText = (EditText) input.findViewById(R.id.MapInput);
	  	    	String text = mText.getText().toString();
	  	    	
	  	    	// redirect to search results
	  	    	Bundle bundle = new Bundle();
	  	    	bundle.putString("mail", text);
	
	  	    	Log.v(TAG, "HI");
	  	    	Intent newIntent = new Intent(mContext.getApplicationContext(), Login.class);
	  	    	newIntent.putExtras(bundle);
	  	    	mContext.startActivity(newIntent);
	  	    }
	  	});
	
	  	dialog.setNegativeButton("Hide", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
              dialog.cancel();
          }
      });
	  
	  
	  dialog.show();
	  return true;
	  */
	}
	
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

}
