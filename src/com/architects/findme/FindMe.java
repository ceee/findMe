package com.architects.findme;

import com.architects.findme.R;
import com.architects.findme.R.id;
import com.architects.findme.R.layout;
import com.architects.findme.R.menu;
import com.architects.helper.StateHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class FindMe extends Activity 
{	
	public StateHelper phoneState = new StateHelper();
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon:     Toast.makeText(this, "You pressed the icon!", Toast.LENGTH_LONG).show();
                                break;
            case R.id.text:     Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG).show();
                                break;
            case R.id.icontext: Toast.makeText(this, "You pressed the icon and text!", Toast.LENGTH_LONG).show();
                                break;
        }
        return true;
    }
    
    // VIEWS
    public void drawRegistrationHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Registration.class);
        startActivity(myIntent);
    }

    public void drawLoginHandler(View button) 
    {
    	Intent myIntent = new Intent(button.getContext(), Login.class);
        startActivity(myIntent);
    }
    

}