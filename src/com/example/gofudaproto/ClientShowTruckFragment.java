package com.example.gofudaproto;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link ClientShowTruckFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the
 * {@link ClientShowTruckFragment#newInstance} factory method to create an
 * instance of this fragment.
 *
 */
public class ClientShowTruckFragment extends android.support.v4.app.Fragment implements OnServerManagerListener,ListAdapter{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private FrameLayout mFrameLayout;
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private MainActivity mParentActivity;
	private OnFragmentInteractionListener mListener;
	private ArrayList<CallThumbNailView> mCallThumbNailArray;
	private ArrayList<Truck> mReadyTrucks;
	private boolean isCallCountZero;
	private Button mComeButton;
	private OnClickListener mMenuSelectListener;
	private ProgressBar mProgressBar;
	private ClientViewTruckFragment mViewTruckDetailFragment;
	private ListView mTruckListView;
	private SharedPreferences mPreference;
	private SharedPreferences.Editor mEditor;
	private CallPaper mCurrentCallPaper;
	private Button mDragButton;
	private Button mCancelCallButton;
	private Context mContext;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ClientShowTruckFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ClientShowTruckFragment newInstance(String param1,
			String param2) {
		ClientShowTruckFragment fragment = new ClientShowTruckFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ClientShowTruckFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		
		mPreference = getActivity().getSharedPreferences("gopuda", Activity.MODE_PRIVATE);
		mParentActivity = (MainActivity)getActivity();
		mViewTruckDetailFragment = new ClientViewTruckFragment();
	}
	public void initData(Context context){
		mContext = context;
		mReadyTrucks = new ArrayList<Truck>();
		mCallThumbNailArray = new ArrayList<CallThumbNailView>();
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onActivityCreated(savedInstanceState);
		mFrameLayout = (FrameLayout)getActivity().findViewById(R.id.client_call_frame);
		mProgressBar = new ProgressBar(getActivity().getApplicationContext(), null, android.R.attr.progressBarStyleLarge);
		mProgressBar.setIndeterminate(true);
		mProgressBar.setVisibility(View.VISIBLE);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 100);
		params.gravity = Gravity.CENTER;
		mParentActivity.setHaveToBackToStart(true);
		mProgressBar.setLayoutParams(params);
		mCancelCallButton = (Button)getActivity().findViewById(R.id.bt_event_cancel);
		mTruckListView = (ListView)getActivity().findViewById(R.id.event_list);
		mDragButton = new Button(getActivity().getBaseContext());
		mDragButton.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT, 100));
		mDragButton.setText("drag");
		params = new FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.WRAP_CONTENT, android.widget.FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
		mDragButton.setLayoutParams(params);
		mDragButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		mCancelCallButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelTheCall();
			}
		});
		mFrameLayout.addView(mDragButton);
		mTruckListView.setAdapter(this);
	}
	
	public boolean isTruckEmpty(){
		return mReadyTrucks.isEmpty();
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mParentActivity.setIsHaveToStartFragment(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.client_call, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	private void cancelTheCall(){
		// TODO: 현재보낸 요청을 취소하는 메세지를 서버로 전송한다.
		mParentActivity.getServerManager().doSendCall(ServerManager.CANCEL_REQUEST, makeCancelParams(), this);
	}
	private String makeCancelParams(){
		return "";
	}

	private String makeIndexString(String word){
		return " \""+word+"\"";
	}
	
	public void addTruckToComingTruckList(int callID, String callDescription, String truckName , Truck.TruckType type){
		Truck truck = new Truck();
		truck.setReady(callID, callDescription, truckName, type);
		
		mReadyTrucks.add(truck);
		CallThumbNailView ctView = new CallThumbNailView(mContext);
		ctView.setName(truckName);
		ctView.setLocation(callDescription);
		
		mCallThumbNailArray.add(ctView);
	}
	
	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

	@Override
	public void serverDidEnd(String result) {
		// TODO Auto-generated method stub
		if(result != null){ 
			try {
				JSONObject jobj = new JSONObject(result);
				int truck_id = jobj.getInt("truck_id");
				String cart_img = jobj.getString("cart_img");
				String menu_img = jobj.getString("menu_img");
				String description = jobj.getString("description");
				
				mViewTruckDetailFragment.loadTruckSummary(description, null, null);
				mParentActivity.getServerManager().setImageFromUrl(mViewTruckDetailFragment.mProfileImage, cart_img);
				mParentActivity.getServerManager().setImageFromUrl(mViewTruckDetailFragment.mDetailImage, menu_img);
//				
//				Drawable cart = mParentActivity.getServerManager().loadImage(cart_img);
//				Drawable menu = mParentActivity.getServerManager().loadImage(menu_img);
				
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						mViewTruckDetailFragment.mScrollView.addView(mViewTruckDetailFragment.mDetailImage);
						
					}
				}, 400);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	Handler mHandler = new Handler();
	

	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
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
		return mReadyTrucks.size();

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
		CallThumbNailView tmpView =  (CallThumbNailView)mCallThumbNailArray.get(position);
		Truck tmpTruck = mReadyTrucks.get(position);
		tmpView.setEventNumber(String.valueOf(position));
		tmpView.setName(String.valueOf(tmpTruck.getName()));
		tmpView.setLocation(tmpTruck.getDescription());
		
		mCallThumbNailArray.get(position).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Truck t = mReadyTrucks.get(mCallThumbNailArray.indexOf(v));
				sendViewFoodTruckCall(t.getID());
				Toast.makeText(getActivity().getBaseContext(), "call paper clicked", Toast.LENGTH_SHORT);
				mParentActivity.setBeforeContainer(R.id.client_container);
				mParentActivity.setIsHaveToBackFragment(true);
				setThisFragmentToPrev();
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.client_container, mViewTruckDetailFragment).commit();
			}
		});
		return mCallThumbNailArray.get(position);
	}
	public void setThisFragmentToPrev(){
		mParentActivity.setPrevFragment(this);
	}
	public void sendViewFoodTruckCall(int truck_id){
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_TRUCK_PROFILE, makeGetTruckProfileParam(), this);
	}
	public String makeGetTruckProfileParam(){
		return "{"+mParentActivity.makeIndexString("truck_id")+":"+mParentActivity.makeIndexString(String.valueOf(1))+
				"}";
	}
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
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

}
