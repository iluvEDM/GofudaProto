package com.example.gofudaproto;

import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link ClientViewTruckFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the
 * {@link ClientViewTruckFragment#newInstance} factory method to create an
 * instance of this fragment.
 *
 */
public class ClientViewTruckFragment extends android.support.v4.app.Fragment implements OnServerManagerListener{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private Button mComeButton;
	private MainActivity mParentActivity;
	private OnFragmentInteractionListener mListener;
	private int current_truck_id;
	private int current_request_id;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ClientViewTruckFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ClientViewTruckFragment newInstance(String param1,
			String param2) {
		ClientViewTruckFragment fragment = new ClientViewTruckFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ClientViewTruckFragment() {
		// Required empty public constructor
	}
	
	public void setTruckAndRequestIds(int tid, int rid){
		current_request_id = rid;
		current_truck_id = tid;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		mParentActivity = (MainActivity)getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.client_truck_description, container,
				false);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mComeButton = (Button)getActivity().findViewById(R.id.come_button);
		mComeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendComeRequestToServer();
			}
		});
	}
	public String makeComeRequestParams(){
		return "{"+mParentActivity.makeIndexString("truck_id")+":"+mParentActivity.makeIndexString(String.valueOf(current_truck_id)) + ","
				+mParentActivity.makeIndexString("request_id")+":"+mParentActivity.makeIndexString(String.valueOf(current_request_id))+"}";
				
	}
	public void sendComeRequestToServer(){
		mParentActivity.getServerManager().doSendCall(ServerManager.SEND_CUSTOMER_COME_REQUEST, makeComeRequestParams(), this);
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

	@Override
	public void serverDidEnd(String result) {
		// TODO Auto-generated method stub
		if(result != null){
			Toast.makeText(getActivity().getApplicationContext(), "send successfully!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
	}

}
