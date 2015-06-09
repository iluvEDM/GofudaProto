package com.example.gofudaproto;


import java.util.ArrayList;

import com.example.gofudaproto.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class TruckCallFragment extends Fragment{
	
//	private View mBackgroundView;
	private MainActivity mParentActivity;
	private ScrollView mWentEventView;
	private LinearLayout mEventContainLayout;
	private ArrayList<CallPaper> mCallArray;
	private ArrayList<View> mCallThumbnailArray;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mParentActivity = (MainActivity)getActivity();
		mContext = mParentActivity.getBaseContext();
		mWentEventView = (ScrollView)mParentActivity.findViewById(R.id.event_calls);
		mEventContainLayout = new LinearLayout(mContext);
		mEventContainLayout.setLayoutParams(new LayoutParams(mWentEventView.getWidth(), mWentEventView.getHeight()));
		mEventContainLayout.setOrientation(LinearLayout.VERTICAL);
		mCallArray = new ArrayList<CallPaper>();
		mCallThumbnailArray = new ArrayList<View>();
		updateCallinEventView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.truck_call, container, false);
//		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private Context mContext;
	
	private void updateCallinEventView(){
		getCallPapersFromServer();
		int currentY = 0;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(int i=0; i<mCallArray.size();i++){
//			View tmpTNail = inflater.inflate(R.layout.call_thumbnail, null);
			CallThumbNailView tmpTNail = new CallThumbNailView(mContext);
			tmpTNail.setLayoutParams(new LayoutParams(this.getView().getWidth(), 50));
			tmpTNail.setLeft(0);
			tmpTNail.setTop(currentY);
			currentY += 50;
			mCallThumbnailArray.add(tmpTNail);
			mEventContainLayout.addView(tmpTNail);
		}
		
		mWentEventView.addView(mEventContainLayout);
		
		
	}
	
	private void getCallPapersFromServer(){
		for(int i=0; i<10 ; i++){
			mCallArray.add(new CallPaper());
		}
	}

}
