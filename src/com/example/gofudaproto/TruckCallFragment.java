package com.example.gofudaproto;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gofudaproto.R;
import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

public class TruckCallFragment extends Fragment implements   OnServerManagerListener{
	
//	private View mBackgroundView;
	private MainActivity mParentActivity;
	private ListView mEventListView;
	private LinearLayout mEventContainLayout;
	private ArrayList<CallPaper> mCallArray;
	private ArrayList<View> mCallThumbnailArray;
	private Button mTimeButton;
	private GpsInfo mGPS;
	private ArrayAdapter<View> adapter;
	private BaseAdapter mAdapter;
	private TruckConfirmFragment mConfirmFragment;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mParentActivity = (MainActivity)getActivity();
		mContext = getActivity().getBaseContext();
		mCallArray = new ArrayList<CallPaper>();
		mCallThumbnailArray = new ArrayList<View>();
		    
//		    listview.setAdapter(adapter);
		mAdapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				CallThumbNailView tmpView =  (CallThumbNailView)mCallThumbnailArray.get(position);
				tmpView.setEventNumber(String.valueOf(position));
				
				
				mCallThumbnailArray.get(position).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						setThisFragmentStartFragment();
						int index = mCallThumbnailArray.indexOf(v);
						mConfirmFragment.current_request_id = mCallArray.get(index).getCallId();
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.call_contain, mConfirmFragment).commit();
						Toast.makeText(mContext, "call paper clicked", Toast.LENGTH_SHORT).show();
					}
				});
				return mCallThumbnailArray.get(position);
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mCallArray.size();
			}
		};
		mEventListView = (ListView)getActivity().findViewById(R.id.event_list);
		
		mEventListView.setAdapter(mAdapter);
		mEventContainLayout = (LinearLayout)getActivity().findViewById(R.id.event_linear);
		mTimeButton = (Button)getActivity().findViewById(R.id.call_time);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
//		updateCallinEventView();
		getCallPapersFromServer();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mConfirmFragment = new TruckConfirmFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.truck_call, container, false);
//		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private Context mContext;
	public void setThisFragmentStartFragment(){
		mParentActivity.setPrevFragment(this);
		mParentActivity.setIsHaveToBackFragment(true);
		mParentActivity.setBeforeContainer(R.id.call_contain);
	}

//	private void updateCallinEventView(){
//		getCallPapersFromServer();
//		int currentY = 0;
//		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
//		
////		mWentEventView.addView(mEventContainLayout);
//		
//		
//	}
	
	// 임시로 트럭 아이디는 1입니다.
	
	private void getCallPapersFromServer(){
		String param = makeGetRequestsParams(1, mParentActivity.getGPS().getLatitude(), mParentActivity.getGPS().getLongitude());
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_INCOMPLETE_REQUESTS, makeGetRequestsParams(1, mParentActivity.getGPS().getLatitude(), mParentActivity.getGPS().getLongitude()), this);
		
	}
	
	private String makeGetRequestsParams(int truck_id, double lat, double longitude){
		
		return "{"+mParentActivity.makeIndexString("truck_id")+":"+ mParentActivity.makeIndexString(String.valueOf(truck_id)) + ","+mParentActivity.makeIndexString("position")+":"+mParentActivity.makeIndexString(makeLocationToPositionString(lat, longitude)) +"}";
	}
	private String makeLocationToPositionString(double lat, double longitude){
		return String.valueOf(lat)+","+String.valueOf(longitude);
	}
	private void getRequestPaperInformation(){
//		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_REQUEST, param, this);
	}

	@Override
	public void serverDidEnd(String result) {
		// TODO Auto-generated method stub
		
		try {
			JSONArray array = new JSONArray(result);
			
			for(int i=0; i<array.length();i++){
//				JSONObject jobj = array.getJSONObject(i);
				
				JSONArray jar = array.getJSONArray(i);				
				//request_id, name, etc 순의 어레이입니다.
				
				
				int request_id = jar.getInt(0);//jobj.getInt("request_id");
				String name = jar.getString(1);//jobj.getString("name");
				String etc = jar.getString(2);//jobj.getString("etc");
				CallPaper cp = new CallPaper();
				cp.setEventName(name);
				cp.setCallLocation(etc);
				cp.setCallId(request_id);
				mCallArray.add(cp);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<mCallArray.size();i++){
			CallThumbNailView tmpTNail = new CallThumbNailView(mContext);
			tmpTNail.setLayoutParams(new AbsListView.LayoutParams(getActivity().getWindow().getAttributes().width, 150));
			tmpTNail.setName(mCallArray.get(i).getEventName());
			tmpTNail.setLocation(mCallArray.get(i).getCallLocation());
			//주소 어떻게 얻어오지?
			mCallThumbnailArray.add(tmpTNail);
		}
		mAdapter.notifyDataSetChanged();
		
	}

	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
	}

}
