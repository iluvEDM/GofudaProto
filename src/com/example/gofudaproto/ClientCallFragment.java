package com.example.gofudaproto;

import java.util.ArrayList;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ClientCallFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ClientCallFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class ClientCallFragment extends android.support.v4.app.Fragment implements ListAdapter{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private MainActivity mParentActivity;
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private ArrayList<CallPaper> mReadyCallArray;
	private ArrayList<CallThumbNailView> mCallThumbNailArray;
	private boolean isCallCountZero;
	private OnFragmentInteractionListener mListener;

	private Button mSelectDining;
	private Button mSelectDesert;
	private Button mSelectBeverage;
	private TextView mDiningNumberView;
	private TextView mDesertNumberView;
	private TextView mBeverageNumberView;
	private OnClickListener mMenuSelectListener;
	private LinearLayout mMenuLayout;
	
	private Button mSelectCurrentLocationButton;
	private Button mSelectLocationButton;
	private Button mComeButton;
	
	private CallPaper mCurrentCallPaper;
	
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
		mReadyCallArray = new ArrayList<CallPaper>();
		mCallThumbNailArray = new ArrayList<CallThumbNailView>();
		updateClientCalls();
		if(mReadyCallArray.size()<1){
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
		if(isCallCountZero){
			mSelectDining = (Button)getActivity().findViewById(R.id.call_button_dining);
			mSelectDesert = (Button)getActivity().findViewById(R.id.call_button_desert);
			mSelectBeverage = (Button)getActivity().findViewById(R.id.call_button_beverage);
			mDiningNumberView = (TextView)getActivity().findViewById(R.id.call_number_dining);
			mDesertNumberView = (TextView)getActivity().findViewById(R.id.call_number_desert);
			mBeverageNumberView = (TextView)getActivity().findViewById(R.id.call_number_beverage);
			mSelectLocationButton = (Button)getActivity().findViewById(R.id.call_button_location);
			mSelectCurrentLocationButton = (Button)getActivity().findViewById(R.id.call_button_location_current);
			mMenuLayout = (LinearLayout)getActivity().findViewById(R.id.callpaper_container);
		}else{
			mCancelCallButton = (Button)getActivity().findViewById(R.id.bt_event_cancel);
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
					mParentActivity.isHaveToUseCurrentLocation = true;
					Intent i = new Intent(getActivity().getBaseContext(), MapManager.class);
					startActivity(i);
					break;
				}
				
				case R.id.call_button_location:{
					Intent i = new Intent(getActivity().getBaseContext(), MapManager.class);
					startActivity(i);
					break;
				}
				
				case R.id.bt_event_cancel:
					break;

				}
				
			}
		};
		
		mSelectDining.setOnClickListener(mMenuSelectListener);
		mSelectDesert.setOnClickListener(mMenuSelectListener);
		mSelectBeverage.setOnClickListener(mMenuSelectListener);
		mSelectCurrentLocationButton.setOnClickListener(mMenuSelectListener);
		mSelectLocationButton.setOnClickListener(mMenuSelectListener);
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
		return null;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return mReadyCallArray.size();
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

}
