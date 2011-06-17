package com.architects.findme;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivityGroup extends ActivityGroup{

@Override
protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      //you van get the local activitymanager to start the new activity

      View view = getLocalActivityManager().startActivity("Nearby", new Intent(this, Login.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                .getDecorView();
       this.setContentView(view);

   }
}