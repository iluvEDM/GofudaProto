package com.example.gofudaproto;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TruckCheckFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link TruckCheckFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class TruckCheckFragment extends android.support.v4.app.Fragment implements OnServerManagerListener{
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
	private Button mConfirmButton;
	private boolean isGoSign = false;
	private ArrayList<Integer>mAcceptedRequests;
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
	public static TruckCheckFragment newInstance(String param1, String param2) {
		TruckCheckFragment fragment = new TruckCheckFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TruckCheckFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		mAcceptedRequests = new ArrayList<Integer>();
		mParentActivity = (MainActivity)getActivity();
	}

	public void setRequestData(int id, int customer_id, int state, String name,int size, String position, String time, String need_time, String etc, String truck_count){
		mEventName.setText(name);
		mPeopleNumber.setText(String.valueOf(size));
		setMenuNumbers(truck_count);
		mEstimatedTime.setText(time);
		mClientName.setText("someone");
		mExtraRequirement.setText(etc);
		mUseTime.setText(need_time);
		mArrivalTime.setText(time);
	}
	public void setMenuNumbers(String count_string){
		String sub = count_string.substring(1, count_string.length()-1);
		String[] tokens = sub.split(",");
		mDinningNumber.setText(tokens[0]);
		mDesertNumber.setText(tokens[1]);
		mBeverageNumber.setText(tokens[2]);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
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
		mConfirmButton = (Button)getActivity().findViewById(R.id.button_confirm);
		mConfirmButton.setVisibility(View.INVISIBLE);
		sendGetAcceptedRequest();
		
	}
	//원랜 1이아니라 트럭아이디를 넣어야 합니
	public void sendGoRequest(){
		mParentActivity.getServerManager().doSendCall(ServerManager.SEND_TRUCK_GO_REQUEST, makeGoRequestParams(1,current_request_id), this);
		isGoSign = true;
	}
	
	public String makeGoRequestParams(int truck_id, int request_id){
		return "{"+makeIndexString("truck_id")+":"+ makeIndexString(String.valueOf(truck_id))+"," 
				+makeIndexString("request_id")+":"+ makeIndexString(String.valueOf(request_id)) +"}";
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
	//1대신 트럭아이디를 받아야 함 
	public void sendGetAcceptedRequest(){
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_ACCEPTED_REQUEST, makeGetRequestsParams(Integer.parseInt(mParentActivity.getRegisterId(true))), this);
	}
	private String makeGetRequestsParams(int truck_id){

		return "{"+makeIndexString("truck_id")+":"+ makeIndexString(String.valueOf(truck_id)) +"}";
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
	private boolean isGetRequestInformation = false;
	public void sendGetRequestInformationCall(int request_id){
		isGetRequestInformation = true;
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_REQUEST, makeRequestsParams(request_id), this);
	}
	private String makeRequestsParams(int request_id){

		return "{"+makeIndexString("id")+":"+ makeIndexString(String.valueOf(request_id)) +"}";
	}
	@Override
	public void serverDidEnd(String result) {
		// TODO Auto-generated method stub
		if (isGetRequestInformation) {
			try {
				JSONObject jobj = new JSONObject(result);
				int id = jobj.getInt("id");
				int customer_id = jobj.getInt("customer_id");
				int state = jobj.getInt("state");
				String name = jobj.getString("name");
				int size = jobj.getInt("size");
				String position = jobj.getString("position");
				String time = jobj.getString("time");
				String need_time = jobj.getString("need_time");
				String etc = jobj.getString("etc");
				String truck_count = jobj.getString("truck_count");


				setRequestData(id, customer_id, state, name, size, position, time, need_time, etc, truck_count);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
//					JSONObject jobj = array.getJSONObject(i);
					int r_id = Integer.parseInt(array.getString(i));
					mAcceptedRequests.add(r_id);
				}

				if (!mAcceptedRequests.isEmpty()) {
					sendGetRequestInformationCall(mAcceptedRequests.get(mAcceptedRequests.size()-1));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
	}

}
