package com.example.gofudaproto;

import com.example.gofudaproto.server.ServerManagerTest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link IntroFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link IntroFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class IntroFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	public static final String PROPERTY_REG_ID = "registration_id";
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	
	private Button buttonTruck;
	private Button buttonClient;
	private OnClickListener buttonListner;
	
	private Fragment nextFragment;
	private SharedPreferences mPreference;
	MainActivity parentActivity;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment IntroFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static IntroFragment newInstance(String param1, String param2) {
		IntroFragment fragment = new IntroFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public IntroFragment() {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_intro, container, false);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onActivityCreated(savedInstanceState);
		parentActivity = (MainActivity)getActivity();
		parentActivity.setPrevFragment(this);
		buttonTruck = (Button)parentActivity.findViewById(R.id.bt_truck_install);
		buttonClient = (Button)parentActivity.findViewById(R.id.bt_client_install);
		
		buttonListner = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				ServerManagerTest.test1();
				
				
				
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.bt_truck_install:
//					nextFragment = new TruckFragment();
					parentActivity.sendRegistrationIdToBackend(true);
					break;
				case R.id.bt_client_install:
//					nextFragment = new ClientFragment();
//					ClientFragment cf = (ClientFragment)nextFragment;
					parentActivity.sendRegistrationIdToBackend(false);
					break;
				}
//				parentActivity.setBeforeContainer(R.id.container);
				setThisFragmentStartFragment();
//				parentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, nextFragment).commit();
			}
		};
		
		buttonTruck.setOnClickListener(buttonListner);
		buttonClient.setOnClickListener(buttonListner);
		mPreference = parentActivity.getSharedPreferences("gopuda", Activity.MODE_PRIVATE);
	}

	public void setThisFragmentStartFragment(){
		parentActivity.setStartFragment(this);
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

}
