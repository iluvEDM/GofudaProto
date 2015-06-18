package com.example.gofudaproto;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClientReviewView extends LinearLayout {
	public static interface SendReviewListener {
		void sendThisReviewToServer(int id, String content);
	}
	private TextView mName;
	private SendReviewListener mListener;
	private TextView mStar;
	private EditText mDescription;
	private Button mRegisterButton;
	public int truck_id=0;
	public int request_id=0;
	public int customer_id=0;
	public int id=0;
	public void setReviewListener(SendReviewListener listener){
		mListener = listener;
	}
	public ClientReviewView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init(){
		inflate(getContext(), R.layout.client_review, this);
		this.mName = (TextView)findViewById(R.id.review_truckname);
		this.mDescription = (EditText)findViewById(R.id.review_description);
		this.mStar = (TextView)findViewById(R.id.review_star);
		this.mRegisterButton = (Button)findViewById(R.id.review_register_button);
		this.mName.setText("name");
		this.mDescription.setText("description");
		
		mRegisterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.sendThisReviewToServer(id, mDescription.getText().toString());
			}
		});
		
	}
	
	public TextView getName() {
		return mName;
	}
	public void setName(String name) {
		this.mName.setText(name);
	}
	public TextView getDescription() {
		return mDescription;
	}
	public void setDescription(String location) {
		this.mDescription.setText(location);
	}
	public TextView getStar() {
		return mStar;
	}
	public void setStar(String number) {
		this.mStar.setText(number);
	}
	
}
