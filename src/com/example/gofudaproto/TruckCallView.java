package com.example.gofudaproto;


import java.util.ArrayList;

import com.example.gofudaproto.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class TruckCallView{
	
	private View mBackgroundView;
	private MainActivity mParentActivity;
	private ScrollView mCallingEventView;
	private ScrollView mWentEventView;
	private ArrayList<CallPaper> mCallArray;
	private ArrayList<View> mCallThumbnailArray;
	private Context mContext;
	public TruckCallView(Context context, View parentView) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		mBackgroundView = inflater.inflate(R.layout.truck_call , null ,false);
		mBackgroundView.setLayoutParams(new LayoutParams(parentView.getWidth(), parentView.getHeight()));
		mWentEventView = (ScrollView)mParentActivity.findViewById(R.id.event_calls);
		mCallArray = new ArrayList<CallPaper>();
		mCallThumbnailArray = new ArrayList<View>();
		
	}
	
	private void updateCallinEventView(){
		getCallPapersFromServer();
		int currentY = 0;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(int i=0; i<mCallArray.size();i++){
			View tmpTNail = inflater.inflate(R.layout.call_thumbnail, null);
			tmpTNail.setLayoutParams(new LayoutParams(mBackgroundView.getWidth(), 50));
			tmpTNail.setLeft(0);
			tmpTNail.setTop(currentY);
			currentY += 50;
			mCallThumbnailArray.add(tmpTNail);
		}
	}
	
	private void getCallPapersFromServer(){
		
	}

}
