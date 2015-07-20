package com.example.gofudaproto;

import net.daum.mf.map.api.MapReverseGeoCoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gofudaproto.MapViewFragment.CurrentLocationChangedListner;
import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link ClientMakeRequestFagment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the
 * {@link ClientMakeRequestFagment#newInstance} factory method to create an
 * instance of this fragment.
 *
 */
public class ClientMakeRequestFagment extends android.support.v4.app.Fragment implements OnServerManagerListener, CurrentLocationChangedListner{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	
	private MapViewFragment mMapFragment;
	private Button mSelectDining;
	private Button mSelectDesert;
	private Button mSelectBeverage;
	private TextView mDiningNumberView;
	private TextView mDesertNumberView;
	private TextView mBeverageNumberView;
	private OnClickListener mMenuSelectListener;
	private Button mSelectCurrentLocationButton;
	private Button mSelectLocationButton;
	private Button mComeButton;
	private FrameLayout mFrameLayout;
	private ProgressBar mProgressBar;
	private ClientViewTruckFragment mViewTruckDetailFragment;
	private MainActivity mParentActivity;
	private EditText mEventNameTextView;
	private EditText mPeopleCountTextView;;
	private SharedPreferences mPreference;
	private SharedPreferences.Editor mEditor;
	private double mCurrentLatitude = 0;
	private double mCurrentLongitude = 0;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ClientMakeRequestFagment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ClientMakeRequestFagment newInstance(String param1,
			String param2) {
		ClientMakeRequestFagment fragment = new ClientMakeRequestFagment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ClientMakeRequestFagment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		
		mParentActivity = (MainActivity)getActivity();
		mMapFragment = new MapViewFragment();
		mViewTruckDetailFragment = new ClientViewTruckFragment();
		mPreference = getActivity().getSharedPreferences("gopuda", Activity.MODE_PRIVATE);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mParentActivity.setIsHaveToStartFragment(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		mMasterLayout = (LinearLayout)getActivity().findViewById(R.id.client_call_layout);
		mFrameLayout = (FrameLayout)getActivity().findViewById(R.id.client_call_frame);
		mProgressBar = new ProgressBar(getActivity().getApplicationContext(), null, android.R.attr.progressBarStyleLarge);
		mProgressBar.setIndeterminate(true);
		mProgressBar.setVisibility(View.VISIBLE);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 100);
		params.gravity = Gravity.CENTER;
		mParentActivity.setHaveToBackToStart(true);
		mProgressBar.setLayoutParams(params);
		mEventNameTextView = (EditText)getActivity().findViewById(R.id.call_event_name);
		mPeopleCountTextView = (EditText)getActivity().findViewById(R.id.call_people_num);
		mSelectDining = (Button)getActivity().findViewById(R.id.call_button_dining);
		mSelectDesert = (Button)getActivity().findViewById(R.id.call_button_desert);
		mSelectBeverage = (Button)getActivity().findViewById(R.id.call_button_beverage);
		mDiningNumberView = (TextView)getActivity().findViewById(R.id.call_number_dining);
		mDesertNumberView = (TextView)getActivity().findViewById(R.id.call_number_desert);
		mBeverageNumberView = (TextView)getActivity().findViewById(R.id.call_number_beverage);
		mSelectLocationButton = (Button)getActivity().findViewById(R.id.call_button_location);
		mSelectCurrentLocationButton = (Button)getActivity().findViewById(R.id.call_button_location_current);
		mComeButton = (Button)getActivity().findViewById(R.id.call_button_come);
//		mMenuLayout = (LinearLayout)getActivity().findViewById(R.id.callpaper_container);
		mMapFragment.setLocationListner(this);
		mMenuSelectListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int currentNumber = 0;
				switch(v.getId()){
				case R.id.call_button_dining:
					currentNumber = Integer.parseInt(mDiningNumberView.getText().toString());
					if (currentNumber > 5) {
						currentNumber = 0;
					}else{
						currentNumber++;
					}
					mDiningNumberView.setText(String.valueOf(currentNumber));
					break;
				case R.id.call_button_desert:
					currentNumber = Integer.parseInt(mDesertNumberView.getText().toString());
					if (currentNumber > 5) {
						currentNumber = 0;
					}else{
						currentNumber++;
					}
					mDesertNumberView.setText(String.valueOf(currentNumber));
					break;
				case R.id.call_button_beverage:
					currentNumber = Integer.parseInt(mBeverageNumberView.getText().toString());
					if (currentNumber > 5) {
						currentNumber = 0;
					}else{
						currentNumber++;
					}
					mBeverageNumberView.setText(String.valueOf(currentNumber));
					break;
				case R.id.call_button_location_current:{
					mCurrentLatitude = mParentActivity.getGPS().getLatitude();
					mCurrentLongitude = mParentActivity.getGPS().getLongitude();
					Toast.makeText(getActivity().getApplicationContext(), "current location selected!", Toast.LENGTH_SHORT).show();
					break;
				}

				case R.id.call_button_location:{
					mMapFragment.isHaveToCurrentLocation = false;
					mParentActivity.setBeforeContainer(R.id.client_container);
					makeThisFragmentToPrevFragment();
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.client_container, mMapFragment).commit();
					break;
				}

				case R.id.call_button_come:
					sendTheCall();
					break;
				}
			}
		};
		
		mSelectDining.setOnClickListener(mMenuSelectListener);
		mSelectDesert.setOnClickListener(mMenuSelectListener);
		mSelectBeverage.setOnClickListener(mMenuSelectListener);
		mSelectCurrentLocationButton.setOnClickListener(mMenuSelectListener);
		mSelectLocationButton.setOnClickListener(mMenuSelectListener);
		mComeButton.setOnClickListener(mMenuSelectListener);
	}
	
	private void makeThisFragmentToPrevFragment(){
		mParentActivity.setPrevFragment(this);
	}
	private void sendTheCall(){
		// TODO: 현재 제작된 요청서를 서버로 전달한다.
		if(isHasNoMenuSelect()){
			Toast.makeText(getActivity().getBaseContext(), "메뉴를 하나이상 선택해주세요", Toast.LENGTH_SHORT).show();
		}else{
//			String param = makeMenuCallParameter(900917, 0, "customer", 1, "30,128", makeTimeString(1, 30), "come fast");
			mParentActivity.getServerManager().doSendCall(ServerManager.SEND_REQUEST, makeMenuCallParameter(Integer.parseInt(mParentActivity.getRegisterId(false)), 10, mEventNameTextView.getText().toString(), 
					Integer.parseInt(mPeopleCountTextView.getText().toString().trim()), makeLocationToPositionString(mCurrentLatitude, mCurrentLongitude), makeTimeString(1, 30), "come fast"), this);
		}
//		mIsSendRequest = true;
//		mIsShowTruckRequest = false;
		mFrameLayout.addView(mProgressBar);
	}
	private String makeLocationToPositionString(double lat, double longitude){
		return String.valueOf(lat)+","+String.valueOf(longitude);
	}
	private String makeMenuCallParameter(int customer_id, int state, String name, int size, String position, String need_time, String etc){
		return "{" 
				+ makeIndexString("customer_id") + ":" + makeIndexString(String.valueOf(customer_id)) + ","
				+ makeIndexString("state") + ":" + makeIndexString(String.valueOf(state)) + ","
				+ makeIndexString("name") + ":" + makeIndexString(name) + ","
				+ makeIndexString("size") + ":" + makeIndexString(String.valueOf(size)) + ","
				+ makeIndexString("position") + ":" + makeIndexString(position) + ","
				+ makeIndexString("need_time") + ":" + makeIndexString(need_time) + ","
				+ makeIndexString("etc") + ":" + makeIndexString(etc) + ","
				+ makeIndexString("truck_count") + ":" + makeIndexString(makeTruckCountString()) + "}";
	}
	private String makeTruckCountString(){

		return String.format("[%d,%d,%d]", getDiningNumber(),getDesertNumber(),getBeverageNumber());

		//		return "["+"\""+getDiningNumber()+","+getDesertNumber()+","+getBeverageNumber()+"]";
	}
	private String makeIndexString(String word){
		return " \""+word+"\"";
	}
	private String makeTimeString(int hour, int minute){
		String hourString ="";
		String minuteString = "";
		if(hour<10){
			hourString = "0"+String.valueOf(hour);
		}else{
			hourString = String.valueOf(hour);
		}
		
		if(minute<10){
			minuteString = "0"+String.valueOf(minute);
		}else{
			minuteString = String.valueOf(minute);
		}
		
		return hourString + ":" + minuteString + ":00";
		
	}
	
	public boolean isHasNoMenuSelect(){
		if(getDiningNumber()<1 && getDesertNumber()<1 && getBeverageNumber()<1){
			return true;
		}
			return false;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.client_callpaper_detail,
				container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}
	public int getDiningNumber(){
		return Integer.parseInt(mDiningNumberView.getText().toString());
	}
	
	public int getDesertNumber(){
		return Integer.parseInt(mDesertNumberView.getText().toString());
	}
	
	public int getBeverageNumber(){
		return Integer.parseInt(mBeverageNumberView.getText().toString());
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
		mFrameLayout.removeView(mProgressBar);
		
		if(result != null){
			int request_id = Integer.parseInt(result);
			mParentActivity.setCurrentRequest(request_id);
			showSettingsAlert();
		}
	}
	public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mParentActivity.getMainContext());
 
        alertDialog.setTitle("요청서");
        alertDialog.setMessage("요청서가 성공적으로 전송 되었습니다.");
   
        // OK 를 누르게 되면 설정창으로 이동합니다. 
        alertDialog.setPositiveButton("확인", 
                                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	dialog.cancel();
            }
        });
 
        alertDialog.show();
    }
	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentLocation(double latitude, double longitude) {
		// TODO Auto-generated method stub
		mCurrentLatitude = latitude;
		mCurrentLongitude = longitude;
	}

}
