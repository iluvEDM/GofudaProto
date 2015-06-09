package com.example.gofudaproto;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TruckFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link TruckFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class TruckFragment extends Fragment implements OnClickListener{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private MainActivity mParentActivity;
	private OnFragmentInteractionListener mListener;
	private Button mCallButton;
	private Button mConfirmButton;
	private Button mReviewButton;
	private Button mRecipeButton;
	private TruckCallFragment mCallFragment;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment TruckFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static TruckFragment newInstance(String param1, String param2) {
		TruckFragment fragment = new TruckFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TruckFragment() {
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
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_truck, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mParentActivity = (MainActivity)getActivity();
		mParentActivity.setHaveToBack(true);
		mCallButton = (Button)mParentActivity.findViewById(R.id.bt_call);
		mCallButton.setOnClickListener(this);
		mConfirmButton = (Button)mParentActivity.findViewById(R.id.bt_confirm);
		mConfirmButton.setOnClickListener(this);
		mReviewButton = (Button)mParentActivity.findViewById(R.id.bt_review);
		mReviewButton.setOnClickListener(this);
		mRecipeButton = (Button)mParentActivity.findViewById(R.id.bt_recipe);
		mRecipeButton.setOnClickListener(this);
		
		mCallFragment = new TruckCallFragment();
		
		mParentActivity.getSupportFragmentManager().beginTransaction()
		.replace(R.id.call_contain, mCallFragment).commit();
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
//		if (mListener != null) {
//			mListener.onFragmentInteraction(uri);
//		}
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bt_call:
			mParentActivity.getSupportFragmentManager().beginTransaction()
			.replace(R.id.call_contain, mCallFragment).commit();
			break;
		case R.id.bt_confirm:
			break;
		case R.id.bt_review:
			break;
		case R.id.bt_recipe:
			break;
		
		}
		
	}

}
