package com.example.gofudaproto;

import com.example.gofudaproto.server.ServerManager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TruckConfirmFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link TruckConfirmFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class TruckConfirmFragment extends android.support.v4.app.Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	public int current_request_id;
	private String mParam1;
	private String mParam2;
	private MainActivity mParentActivity;
	private OnFragmentInteractionListener mListener;
	private TextView mEventName;
	private TextView mPeopleNumber;
	private TextView mDinningNumber;
	private TextView mDesertNumber;
	private TextView mBeverageNumber;
	private TextView mEstimatedTime;
	private Button mLocationButton;
	private TextView mExtraRequirement;
	private TextView mUseTime;
	private TextView mArrivalTime;
	private TextView mClientName;
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
	public static TruckConfirmFragment newInstance(String param1, String param2) {
		TruckConfirmFragment fragment = new TruckConfirmFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TruckConfirmFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		private TextView mEventName;
//		private TextView mPeopleNumber;
//		private TextView mDinningNumber;
//		private TextView mDesertNumber;
//		private TextView mBeverageNumber;
//		private TextView mEstimatedTime;
//		private Button mLocationButton;
//		private TextView mExtraRequirement;
//		private TextView mUseTime;
//		private TextView mArrivalTime;
//		private TextView mClientName;
		mEventName = (TextView)getActivity().findViewById(R.id.call_event_name);
		mPeopleNumber = (TextView)getActivity().findViewById(R.id.call_people_num);
		mDinningNumber = (TextView)getActivity().findViewById(R.id.call_number_dining);
		mDesertNumber = (TextView)getActivity().findViewById(R.id.call_number_desert);
		mBeverageNumber = (TextView)getActivity().findViewById(R.id.call_number_beverage);
		mEstimatedTime = (TextView)getActivity().findViewById(R.id.call_estimated_time);
		mLocationButton = (Button)getActivity().findViewById(R.id.call_button_location);
		mExtraRequirement = (TextView)getActivity().findViewById(R.id.call_extra_require);
		mUseTime = (TextView)getActivity().findViewById(R.id.call_duration);
		mArrivalTime = (TextView)getActivity().findViewById(R.id.call_arrival_time);
		mClientName = (TextView)getActivity().findViewById(R.id.call_client);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.callpaper_detail, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}
	public void sendGetRequestInformationCall(){
//		mParentActivity.getServerManager().doSendCall(ServerManager.CANCEL_REQUEST, makeCancelParams(), this);
	}
	private String makeGetRequestsParams(int truck_id, double lat, double longitude){

//		return "{"+mParentActivity.makeIndexString("truck_id")+":"+ mParentActivity.makeIndexString(String.valueOf(truck_id)) + ","+mParentActivity.makeIndexString("position")+":"+mParentActivity.makeIndexString(makeLocationToPositionString(lat, longitude)) +"}";
		return null;
	}
	private String makeIndexString(String word){
		return " \""+word+"\"";
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

}
