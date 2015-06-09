package com.example.gofudaproto;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CallThumbNailView extends LinearLayout {
	
	private TextView mName;
	private TextView mLocation;
	private TextView mEventNumber;
	public CallThumbNailView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init(){
		inflate(getContext(), R.layout.call_thumbnail, this);
		this.mName = (TextView)findViewById(R.id.call_thumb_name);
		this.mLocation = (TextView)findViewById(R.id.call_thumb_location);
		this.mEventNumber = (TextView)findViewById(R.id.call_thumb_number);
		this.mName.setText("name");
		this.mLocation.setText("location");
	}
}
