package com.example.gofudaproto;


import java.util.ArrayList;

import com.example.gofudaproto.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

public class TruckCallFragment extends Fragment implements ListAdapter{
	
//	private View mBackgroundView;
	private MainActivity mParentActivity;
	private ListView mEventListView;
	private LinearLayout mEventContainLayout;
	private ArrayList<CallPaper> mCallArray;
	private ArrayList<View> mCallThumbnailArray;
	private Button mTimeButton;
	private Button mLocationButton;
	private Button mPeopleButton;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mParentActivity = (MainActivity)getActivity();
		mContext = getActivity().getBaseContext();
		mCallArray = new ArrayList<CallPaper>();
		mCallThumbnailArray = new ArrayList<View>();
		getCallPapersFromServer();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mEventListView = (ListView)getActivity().findViewById(R.id.event_list);
		mEventListView.setAdapter(this);
//		mWentEventView.setLayoutParams(new LayoutParams(getView().getWidth(), getView().getHeight()*5/7));
		mEventContainLayout = (LinearLayout)getActivity().findViewById(R.id.event_linear);
//		mEventContainLayout.setLayoutParams(new LayoutParams(mWentEventView.getWidth(), mWentEventView.getHeight()));
//		mEventContainLayout.setOrientation(LinearLayout.VERTICAL);
		mTimeButton = (Button)getActivity().findViewById(R.id.call_time);
		
//		updateCallinEventView();
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
	
//	private void updateCallinEventView(){
//		getCallPapersFromServer();
//		int currentY = 0;
//		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
//		
////		mWentEventView.addView(mEventContainLayout);
//		
//		
//	}
	
	private void getCallPapersFromServer(){
		for(int i=0; i<10 ; i++){
			mCallArray.add(new CallPaper());
		}
		for(int i=0; i<mCallArray.size();i++){
			CallThumbNailView tmpTNail = new CallThumbNailView(mContext);
			tmpTNail.setLayoutParams(new AbsListView.LayoutParams(getActivity().getWindow().getAttributes().width, 150));
			mCallThumbnailArray.add(tmpTNail);
		}
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCallArray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CallThumbNailView tmpView =  (CallThumbNailView)mCallThumbnailArray.get(position);
		tmpView.setEventNumber(String.valueOf(position));
		
		
		mCallThumbnailArray.get(position).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "call paper clicked", Toast.LENGTH_SHORT).show();				
				requireCallDetailToServer(mCallArray.get(mCallThumbnailArray.indexOf(v)).getCallId());
				loadCallDetailView();
			}
		});
		return mCallThumbnailArray.get(position);
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return mCallArray.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}
	public void requireCallDetailToServer(int callId){
		
	}
	
	public void loadCallDetailView(){
		
	}
}
