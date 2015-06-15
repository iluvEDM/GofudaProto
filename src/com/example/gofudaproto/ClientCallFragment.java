package com.example.gofudaproto;

import java.util.ArrayList;

import net.daum.android.map.MapView;
import net.daum.android.map.MapViewEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ClientCallFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ClientCallFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class ClientCallFragment extends android.support.v4.app.Fragment implements ListAdapter,MapViewEventListener{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	
	
	
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private MainActivity mParentActivity;
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
//	private ArrayList<CallPaper> mReadyCallArray;
	private ArrayList<CallThumbNailView> mCallThumbNailArray;
	private ArrayList<Truck> mReadyTrucks;
	private boolean isCallCountZero;
	private OnFragmentInteractionListener mListener;
	private MapViewFragment mMapFragment;
	private Button mSelectDining;
	private Button mSelectDesert;
	private Button mSelectBeverage;
	private TextView mDiningNumberView;
	private TextView mDesertNumberView;
	private TextView mBeverageNumberView;
	private OnClickListener mMenuSelectListener;
	private LinearLayout mMenuLayout;
	private LinearLayout mMasterLayout;
	private Button mSelectCurrentLocationButton;
	private Button mSelectLocationButton;
	private Button mComeButton;
	
	private CallPaper mCurrentCallPaper;
	private Button mDragButton;
	private Button mCancelCallButton;
	private NGeoPoint mSelectedLocation;
	
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment TruckConfirmFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ClientCallFragment newInstance(String param1, String param2) {
		ClientCallFragment fragment = new ClientCallFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ClientCallFragment() {
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
		mReadyTrucks = new ArrayList<Truck>();
		mCallThumbNailArray = new ArrayList<CallThumbNailView>();
		updateClientCalls();
		if(mReadyTrucks.size()<1){
			isCallCountZero = true;
//			
		}else{
			isCallCountZero = false;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(isCallCountZero){
			return inflater.inflate(R.layout.client_callpaper_detail, container, false);	
		}
		
		return inflater.inflate(R.layout.client_call, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mMasterLayout = (LinearLayout)getActivity().findViewById(R.id.client_call_layout);
		if(isCallCountZero){
			mSelectDining = (Button)getActivity().findViewById(R.id.call_button_dining);
			mSelectDesert = (Button)getActivity().findViewById(R.id.call_button_desert);
			mSelectBeverage = (Button)getActivity().findViewById(R.id.call_button_beverage);
			mDiningNumberView = (TextView)getActivity().findViewById(R.id.call_number_dining);
			mDesertNumberView = (TextView)getActivity().findViewById(R.id.call_number_desert);
			mBeverageNumberView = (TextView)getActivity().findViewById(R.id.call_number_beverage);
			mSelectLocationButton = (Button)getActivity().findViewById(R.id.call_button_location);
			mSelectCurrentLocationButton = (Button)getActivity().findViewById(R.id.call_button_location_current);
			mComeButton = (Button)getActivity().findViewById(R.id.call_button_come);
			mMenuLayout = (LinearLayout)getActivity().findViewById(R.id.callpaper_container);
			mMapFragment = new MapViewFragment();
			// java code
		}else{
			mCancelCallButton = (Button)getActivity().findViewById(R.id.bt_event_cancel);
			mDragButton = new Button(getActivity().getBaseContext());
			mDragButton.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT, 100));
			mDragButton.setText("drag");
			mDragButton.setRight(200);
			mDragButton.setTop(100);
			mMasterLayout.addView(mDragButton);
		}
		mMenuSelectListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int currentNumber = 0;
				switch(v.getId()){
				case R.id.call_button_dining:
					currentNumber = Integer.parseInt(mDiningNumberView.getText().toString());
					currentNumber++;
					mDiningNumberView.setText(String.valueOf(currentNumber));
					break;
				case R.id.call_button_desert:
					currentNumber = Integer.parseInt(mDesertNumberView.getText().toString());
					currentNumber++;
					mDesertNumberView.setText(String.valueOf(currentNumber));
					break;
				case R.id.call_button_beverage:
					currentNumber = Integer.parseInt(mBeverageNumberView.getText().toString());
					currentNumber++;
					mBeverageNumberView.setText(String.valueOf(currentNumber));
					break;
				case R.id.call_button_location_current:{
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mMapFragment).commit();
					break;
				}
				
				case R.id.call_button_location:{
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mMapFragment).commit();
					break;
				}
				
				case R.id.bt_event_cancel:
					cancelTheCall();
					break;
				case R.id.call_button_come:
					sendTheCall();
					break;
				}
			}
		};
		if(isCallCountZero){
			mSelectDining.setOnClickListener(mMenuSelectListener);
			mSelectDesert.setOnClickListener(mMenuSelectListener);
			mSelectBeverage.setOnClickListener(mMenuSelectListener);
			mSelectCurrentLocationButton.setOnClickListener(mMenuSelectListener);
			mSelectLocationButton.setOnClickListener(mMenuSelectListener);
			mComeButton.setOnClickListener(mMenuSelectListener);
		}else{
		
		mCancelCallButton.setOnClickListener(mMenuSelectListener);
		}
	}
	private void sendTheCall(){
		// TODO: 현재 제작된 요청서를 서버로 전달한다.
	}
	private void cancelTheCall(){
		// TODO: 현재보낸 요청을 취소하는 메세지를 서버로 전송한다.
	}
	private void loadReadyTrucksFromServer(){
		// TODO: 서버에서 현재 준비된 트럭 정보를 얻어온 뒤 iteration마다 addTruckToComingTruckList()를 통해 mReadyTrucks에 트럭 정보를 추가한다.
		
	}
	
	private void addTruckToComingTruckList(int callID, String callDescription){
		Truck truck = new Truck();
		truck.setReady(callID, callDescription);
		mReadyTrucks.add(truck);
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
//		try {
//			mListener = (OnFragmentInteractionListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnFragmentInteractionListener");
//		}
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
	
	private void updateClientCalls(){
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CallThumbNailView tmpView =  (CallThumbNailView)mCallThumbNailArray.get(position);
		Truck tmpTruck = mReadyTrucks.get(position);
		tmpView.setEventNumber(String.valueOf(position));
		tmpView.setName(String.valueOf(tmpTruck.getID()));
		tmpView.setLocation(tmpTruck.getDescription());
		
		mCallThumbNailArray.get(position).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getBaseContext(), "call paper clicked", Toast.LENGTH_SHORT);
			}
		});
		return mCallThumbNailArray.get(position);
		
	}
	public void sendReadyTruckListCall(){
		
	}
	public void sendViewFoodTruckCall(int truck_id){
		
	}
	

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return mReadyTrucks.size();
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
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
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLoadMapView() {
		// TODO Auto-generated method stub
		
	}

}
