package com.architects.findme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Chat extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("Chat");
        setContentView(textview);
    }
}
